/**
 * Copyright 2020-2023 the original author or Linlan authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.linlan.commons.core.io;

import io.linlan.commons.core.io.file.FileWriter;
import io.linlan.commons.core.io.file.UnicodeInputStream;
import io.linlan.commons.core.lang.Assert;
import io.linlan.commons.core.ArrayUtils;
import io.linlan.commons.core.CharsetUtils;
import io.linlan.commons.core.ClassUtils;
import io.linlan.commons.core.CollectionUtils;
import io.linlan.commons.core.StringUtils;

import java.io.*;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * File class to provide utils for use
 * Filename:FileUtils.java
 * Desc:File utils to deal with file, and provide methods such as:
 * isEmpty, copy, isExist, isDirectory, listFiles etc.
 * the file type include jar, class, zip, doc and other extension.
 *
 * Createtime 2020/7/12 10:13 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class FileUtils {

    /**
     * constructor of self
     */
    private FileUtils() {
    }

    /**
     * Class file extension
     */
    public static final String CLASS_EXT = ".class";
    /**
     * Jar file extension
     */
    public static final String JAR_FILE_EXT = ".jar";

    /**
     * Jar jar path file extension
     */
    public static final String JAR_PATH_EXT = ".jar!";

    /**
     * Path is a file, use prefix to show file, the prefix is:file:
     */
    public static final String PATH_FILE_PRE = "file:";

    /**
     * list the files and directorys in the source path<br>
     * the path is real path, can not be a zipFilePath
     *
     * @param source absolute path or relative path
     * @return {@link File} File Object[]
     */
    public static File[] list(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = getAbsolutePath(source);

        File file = create(source);
        if (file.isDirectory()) {
            return file.listFiles();
        }
        throw new IORuntimeException(StringUtils.format("Path [{}] is not directory!", source));
    }

    /**
     * if the file or file's directory is Empty<br>
     * if is a file, the file size 0 or a directory the directory's size 0, the result is empty
     *
     * @param source input file, source file
     * @return if empty true, else false
     */
    public static boolean isEmpty(File source) {
        if (null == source) {
            return true;
        }

        if (source.isDirectory()) {
            String[] subFiles = source.list();
            if (ArrayUtils.isEmpty(subFiles)) {
                return true;
            }
        } else if (source.isFile()) {
            return source.length() <= 0;
        }

        return false;
    }

    /**
     * if the file or file's directory is not Empty<br>
     * if is a file, the file size 0 or a directory the directory's size 0, the result is empty
     *
     * @param source input file, source file
     * @return if not empty true, else false
     */
    public static boolean isNotEmpty(File source) {
        return false == isEmpty(source);
    }

    /**
     * if the directory is Empty<br>
     * if is a directory the directory's size 0, the result is empty
     *
     * @param source input directory path
     * @return if empty true, else false
     * @exception IORuntimeException exception
     */
    public static boolean isDirEmpty(Path source) {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(source)) {
            return false == dirStream.iterator().hasNext();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * if the directory is Empty<br>
     * if is a directory the directory's size 0, the result is empty
     *
     * @param source input file is a directory
     * @return if empty true, else false
     * @exception IORuntimeException exception
     */
    public static boolean isDirEmpty(File source) {
        return isDirEmpty(source.toPath());
    }

    /**
     * loop all files in the File path, include sub directory<br>
     * if input File is a file, then return null, the default fileter is null
     *
     * @param source file or file directory
     * @return {@link List<File>} list of file
     */
    public static List<File> loopFiles(File source) {
        return loopFiles(source, null);
    }

    /**
     * loop all files in the File path, include sub directory<br>
     * if input File is a file, then return fileFilter
     *
     * @param source file or file directory
     * @param fileFilter file filter, to filter files, only to files, nothing to directory
     * @return {@link List<File>} list of file
     */
    public static List<File> loopFiles(File source, FileFilter fileFilter) {
        List<File> fileList = new ArrayList<>();
        if (source == null) {
            return fileList;
        } else if (source.exists() == false) {
            return fileList;
        }

        if (source.isDirectory()) {
            for (File tmp : source.listFiles()) {
                fileList.addAll(loopFiles(tmp, fileFilter));
            }
        } else {
            if (null == fileFilter || fileFilter.accept(source)) {
                fileList.add(source);
            }
        }

        return fileList;
    }

    /**
     * list all the file's names of source path<br>
     * do not scan the sub directory
     *
     * @param source the relative of ClassPath or absolute path
     * @return List<String> the file's name if whole path,
     *                      if the file is jar, return like .jar!/xxx/xxx
     * @throws IORuntimeException exception
     */
    public static List<String> listFileNames(String source) throws IORuntimeException {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = getAbsolutePath(source);
        if (false == source.endsWith(String.valueOf(StringUtils.C_SLASH))) {
            source = source + StringUtils.C_SLASH;
        }

        List<String> paths = new ArrayList<>();
        int index = source.lastIndexOf(FileUtils.JAR_PATH_EXT);
        if (index == -1) {
            // normal path
            File[] files = list(source);
            for (File file : files) {
                if (file.isFile()) {
                    paths.add(file.getName());
                }
            }
        } else {
            // the path in jar file
            index = index + FileUtils.JAR_FILE_EXT.length();
            JarFile jarFile = null;
            try {
                jarFile = new JarFile(source.substring(0, index));
                final String subPath = source.substring(index + 2);
                for (JarEntry entry : Collections.list(jarFile.entries())) {
                    final String name = entry.getName();
                    if (name.startsWith(subPath)) {
                        final String nameSuffix = StringUtils.removePrefix(name, subPath);
                        if (nameSuffix.contains(String.valueOf(StringUtils.C_SLASH)) == false) {
                            paths.add(nameSuffix);
                        }
                    }
                }
            } catch (IOException e) {
                throw new IORuntimeException(StringUtils.format("Can not read file path of [{}]", source), e);
            } finally {
                IoUtils.close(jarFile);
            }
        }
        return paths;
    }


    /**
     * create a File or parent directory, if the file exist return the file
     * the create method do not to judge the file type, because if the file
     * not exist, you can not get the type
     *
     * @param file the source file
     * @return {@link File} File Object, if directory is null, return null
     * @throws IORuntimeException exception
     */
    public static File create(File file) throws IORuntimeException {
        if (null == file) {
            return null;
        }
        if (false == file.exists()) {
            File parentFile = file.getParentFile();
            mkdirs(parentFile);
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new IORuntimeException(e);
            }
        }
        return file;
    }

    /**
     * create a File with source path, the path can be absolute or relative
     * if relative then will search in the classpath directory
     *
     * @param source the source of path
     * @return {@link File} File Object
     */
    public static File create(String source) {
        if (StringUtils.isBlank(source)) {
            throw new NullPointerException("File path is blank!");
        }
        return new File(getAbsolutePath(source));
    }

    /**
     * create a File with source path, the parent path is input
     *
     * @param parent the parent path
     * @param source the source of path
     * @return {@link File} File Object
     */
    public static File create(String parent, String source) {
        if (StringUtils.isBlank(source)) {
            throw new NullPointerException("File path is blank!");
        }
        return new File(parent, source);
    }

    /**
     * create a File with source path, the parent path is input
     *
     * @param parent the parent file
     * @param source the source of path
     * @return {@link File} File Object
     */
    public static File create(File parent, String source) {
        if (StringUtils.isBlank(source)) {
            throw new NullPointerException("File path is blank!");
        }
        return new File(parent, source);
    }

    /**
     * create a File by URL path
     *
     * @param source the source uri path
     * @return {@link File} File Object
     */
    public static File create(URI source) {
        if (source == null) {
            throw new NullPointerException("File uri is null!");
        }
        return new File(source);
    }

    /**
     * make directory, if the directory is exist, return directory<br>
     *
     * @param source the source path, use POSIX format
     * @return File the directory
     */
    public static File mkdirs(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        final File dir = create(source);
        return mkdirs(dir);
    }

    /**
     * Creates the directory named by this abstract pathname, including any
     * necessary but nonexistent parent directories.  Note that if this
     * operation fails it may have succeeded in creating some of the necessary
     * parent directories.
     *
     * @param file the input file
     * @return  <code>true</code> if and only if the directory was created,
     *          along with all necessary parent directories; <code>false</code>
     *          otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method does not permit verification of the existence of the
     *          named directory and all necessary parent directories; or if
     *          the <code>{@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *          method does not permit the named directory and all necessary
     *          parent directories to be created
     */
    public static File mkdirs(File file) {
        if (file == null) {
            return null;
        }
        if (false == file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    /**
     * exist the path, if exist, true, else false, if path is null, false
     *
     * @param source the source of path
     * @return if exist, true, else false
     */
    public static boolean exist(String source) {
        return (StringUtils.isBlank(source)) ? false : create(source).exists();
    }

    /**
     * exist the file, if exist, true, else false, if path is null, false
     *
     * @param file the source file
     * @return if exist, true, else false
     */
    public static boolean exist(File file) {
        return (file == null) ? false : file.exists();
    }

    /**
     * if file name like name exist is directory
     *
     * @param directory file directory
     * @param name name match in regex
     * @return if match true, else false
     */
    public static boolean exist(String directory, String name) {
        File file = new File(directory);
        if (!file.exists()) {
            return false;
        }

        String[] fileList = file.list();
        if (fileList == null) {
            return false;
        }

        for (String fileName : fileList) {
            if (fileName.matches(name)) {
                return true;
            }

        }
        return false;
    }

    /**
     * get the last modify time of file
     *
     * @param file the {@link File} to be opened for reading or writing
     * @return Date the lastModified time
     */
    public static Date lastModified(File file) {
        if (!exist(file)) {
            return null;
        }

        return new Date(file.lastModified());
    }

    /**
     * calculate the total size of file or directory<br>
     * if the file is not a directory, return  {@link File#length()}<br>
     * if the file is not a directory, get all files in sub directory
     * to calculate total size
     *
     * @param file a file or a directory
     * @return size the total size of all files
     */
    public static long size(File file) {
        Assert.notNull(file, "create argument is null !");
        if (false == file.exists()) {
            throw new IllegalArgumentException(StringUtils.format(
                    "File [{}] not exist !", file.getAbsolutePath()));
        }

        if (file.isDirectory()) {
            long size = 0L;
            File[] subFiles = file.listFiles();
            if (ArrayUtils.isEmpty(subFiles)) {
                return 0L;// empty directory
            }
            for (int i = 0; i < subFiles.length; i++) {
                size += size(subFiles[i]);
            }
            return size;
        } else {
            return file.length();
        }
    }

    /**
     * if the file is newer than the reference file
     * use lastModified to judge the two files
     *
     * @param file input file, source file or directory
     * @param reference reference file
     * @return if newer then true, else false
     */
    public static boolean newerThan(File file, File reference) {
        if (null == file || false == reference.exists()) {
            return true;// 文件一定比一个不存在的文件新
        }
        return newerThan(file, reference.lastModified());
    }

    /**
     * if the file is newer than the input timeMillis
     * use lastModified to judge
     *
     * @param file input file, source file or directory
     * @param timeMillis reference timeMillis
     * @return if newer then true, else false
     */
    public static boolean newerThan(File file, long timeMillis) {
        if (null == file || false == file.exists()) {
            return false;
        }
        return file.lastModified() > timeMillis;
    }

    /**
     * delete the file or directory by input path<br>
     * the path can use absolute or relative path
     * if the path is relative, will use the classpath to do operation
     * the delete operation will do all to sub directory
     * if there is a exception, the operation will terminate
     *
     * @param path the path of file or directory
     * @return if success true, else false
     * @throws IORuntimeException exception
     */
    public static boolean delete(String path) throws IORuntimeException {
        return delete(create(path));
    }

    /**
     * delete the file or directory by input file<br>
     * the path can use absolute or relative path
     * if the path is relative, will use the classpath to do operation
     * the delete operation will do all to sub directory
     * if there is a exception, the operation will terminate
     *
     * @param file the file or directory
     * @return if success true, else false
     * @throws IORuntimeException exception
     */
    public static boolean delete(File file) throws IORuntimeException {
        if (file == null || file.exists() == false) {
            return true;
        }

        if (file.isDirectory()) {
            clean(file);
        }
        return file.delete();
    }

    /**
     * clean the directory with all sub directories<br>
     *
     * @param directory directory
     * @return if success true, else false
     * @throws IORuntimeException exception
     */
    public static boolean clean(File directory) throws IORuntimeException {
        if (directory == null || directory.exists() == false || false == directory.isDirectory()) {
            return true;
        }

        final File[] files = directory.listFiles();
        for (File childFile : files) {
            boolean isOk = delete(childFile);
            if (isOk == false) {
                // if error then terminate
                return false;
            }
        }
        return true;
    }

    /**
     * copy source path file or directory to dest path file or directory<br>
     *
     * @param source source path file or directory
     * @param dest dest path file or directory, if not exist then create
     * @param isOverride if override, true override, false no
     * @return File
     * @throws IORuntimeException exception
     */
    public static File copy(String source, String dest, boolean isOverride) throws IORuntimeException {
        return copy(create(source), create(dest), isOverride);
    }

    /**
     * copy source path file or directory to dest path file or directory<br>
     *
     * @param source source path file or directory
     * @param dest dest path file or directory, if not exist then create
     * @return File
     * @throws IORuntimeException exception
     */
    public static File copy(String source, String dest) throws IORuntimeException {
        return copy(create(source), create(dest), true);
    }

    /**
     * copy source file or directory to dest file or directory<br>
     * if source, dest are all directory, copy source and sub directory to dest directory<br>
     * if source, dest are all file, direct copy, the dest file name use dest<br>
     * if source is a file, dest is a directory, copy source file to dest directory<br>
     *
     * @param source source file or directory
     * @param dest dest file or directory, if not exist then create
     * @return File
     * @throws IORuntimeException exception
     */
    public static File copy(File source, File dest) throws IORuntimeException {
        return copy(create(source), create(dest), true);
    }

    /**
     * copy source file or directory to dest file or directory<br>
     * if source, dest are all directory, copy source and sub directory to dest directory<br>
     * if source, dest are all file, direct copy, the dest file name use dest<br>
     * if source is a file, dest is a directory, copy source file to dest directory<br>
     *
     * @param source source file or directory
     * @param dest dest file or directory, if not exist then create
     * @param isOverride if override, true override, false no
     * @return File
     * @throws IORuntimeException exception
     */
    public static File copy(File source, File dest, boolean isOverride) throws IORuntimeException {
        // check
        Assert.notNull(source, "Source File is null !");
        if (false == source.exists()) {
            throw new IORuntimeException("File not exist: " + source);
        }
        Assert.notNull(dest, "Destination File or directiory is null !");
        if (equals(source, dest)) {
            throw new IORuntimeException("Files '" + source + "' and '" + dest + "' are equals");
        }

        if (source.isDirectory()) {// 复制目录
            copyToDir(source, dest, isOverride);
        } else {// 复制文件
            internalCopyFile(source, dest, isOverride);
        }
        return dest;
    }

    /**
     * copy source file or directory to dest directory<br>
     * the dest is directory, copy source file, directory and sub directory to dest directory<br>
     *
     * @param source source file or directory
     * @param dest dest file or directory, if not exist then create
     * @param isOverride if override, true override, false no
     * @return File
     * @throws IORuntimeException exception
     */
    private static void copyToDir(File source, File dest, boolean isOverride) throws IORuntimeException {
        if (false == dest.exists()) {
            dest.mkdirs();
        } else if (dest.isFile()) {
            throw new IORuntimeException(StringUtils.format("Src [{}] is a directory " +
                    "but dest [{}] is a file!", source.getPath(), dest.getPath()));
        }

        final String files[] = source.list();
        for (String file : files) {
            File sourceFile = new File(source, file);
            File destFile = new File(dest, file);
            // do source sub file, directory
            if (sourceFile.isDirectory()) {
                copyToDir(sourceFile, destFile, isOverride);
            } else {
                internalCopyFile(sourceFile, destFile, isOverride);
            }
        }
    }

    /**
     * copy source file to dest file or directory<br>
     * the source is a file, the dest can be a file or directory, copy source file to dest<br>
     *
     * @param source source file or directory
     * @param dest dest file or directory, if not exist then create
     * @param isOverride if override, true override, false no
     * @return File
     * @throws IORuntimeException exception
     */
    private static void internalCopyFile(File source, File dest, boolean isOverride) throws IORuntimeException {
        // copy
        if (false == dest.exists()) {
            //if not exist then create
            create(dest);
        } else if (dest.isDirectory()) {
            // if dest is directory, create a same name with source in directory
            dest = new File(dest, source.getName());
        } else if (false == isOverride) {
            // if the dest already have the name, do not override and continue
            return;
        }

        // do copy file
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            IoUtils.copy(input, output);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtils.close(output);
            IoUtils.close(input);
        }

        // validation
        if (source.length() != dest.length()) {
            throw new IORuntimeException("Copy file failed of '" + source + "' to '" + dest + "' due to different sizes");
        }
    }

    /**
     * move source file or directory to dest
     *
     * @param source source file or directory
     * @param dest dest file or directory, if not exist then create
     * @param isOverride if override, true override, false no
     * @throws IORuntimeException exception
     */
    public static void move(File source, File dest, boolean isOverride) throws IORuntimeException {
        // check
        if (!source.exists()) {
            throw new IORuntimeException("File already exist: " + source);
        }

        // source is a directory, dest is a file
        if (source.isDirectory() && dest.isFile()) {
            throw new IORuntimeException(StringUtils.format("Can not move directory [{}] to file [{}]", source, dest));
        }

        if (isOverride && dest.isFile()) {
            // if dest is a file and override, then override
            dest.delete();
        }

        // if the source is a file, the dest is a directory
        if (source.isFile() && dest.isDirectory()) {
            dest = new File(dest, source.getName());
        }

        if (source.renameTo(dest) == false) {
            // some questions with renameTo in any type OS, so use copy and delete
            try {
                copy(source, dest, isOverride);
                source.delete();
            } catch (Exception e) {
                throw new IORuntimeException(StringUtils.format(
                        "Move [{}] to [{}] failed!", source, dest), e);
            }

        }
    }

    /**
     * get the absolute path of source string<br>
     * the method will not judge the source path, if it is valid or file, directory is exist
     * if input clazz is not null, use {@link Class#getResource}
     * else use {@link ClassLoader#getResource}
     *
     * @param source source path, relative path
     * @param clazz the base class of relative path
     * @return absolute path in OS
     */
    public static String getAbsolutePath(String source, Class<?> clazz) {
        if (StringUtils.isBlank(source)) {
            source = StringUtils.EMPTY;
        } else {
            source = normalize(source);
            if (isAbsolutePath(source)) {
                // the path is absolute path
                return source;
            }
        }

        // to compatible spring classpath, remove prefix of "classpath:"
        source = StringUtils.removePrefix(source, StringUtils.CLASSPATH_PREFIX, true);
        source = StringUtils.removePrefix(source, StringUtils.SLASH, false);

        // the relative path to ClassPath
        final URL url = ClassUtils.getUrl(source, clazz);
        if(null != url){
            return url.getPath();
        }else{
            return ClassUtils.addResourcePathToPackagePath(clazz, source);
        }
    }

    /**
     * get the absolute path of source string<br>
     *
     * @param source source path, relative path
     * @return absolute path in OS
     */
    public static String getAbsolutePath(String source) {
        return getAbsolutePath(source, null);
    }

    /**
     * get the absolute path of source file<br>
     *
     * @param file source file
     * @return absolute path in OS
     */
    public static String getAbsolutePath(File file) {
        if (file == null) {
            return null;
        }

        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }

    /**
     * if the source is absolute path<br>
     * before this, please do normalize {@link #normalize(String)} first
     *
     * @param source source path
     * @return if absolute true, else false
     */
    public static boolean isAbsolutePath(String source){
        if (StringUtils.C_SLASH == source.charAt(0) || source.matches("^[a-zA-Z]:/.*")) {
            return true;
        }
        return false;
    }

    /**
     * if the source path is a directory, if it is a directory true, else false
     * if the path is null, return false
     *
     * @param source source path
     * @return if it is a directory true, else false
     */
    public static boolean isDir(String source) {
        return (StringUtils.isBlank(source)) ? false : create(source).isDirectory();
    }

    /**
     * if the source file is a directory, if it is a directory true, else false
     * if the file is null, return false
     *
     * @param file source file
     * @return if it is a directory true, else false
     */
    public static boolean isDir(File file) {
        return (file == null) ? false : file.isDirectory();
    }

    /**
     * if the source path is a file, if it is a file true, else false
     * if the path is null, return false
     *
     * @param source source path
     * @return if it is a file true, else false
     */
    public static boolean isFile(String source) {
        return (StringUtils.isBlank(source)) ? false : create(source).isFile();
    }

    /**
     * if the source file is a file, if it is a file true, else false
     * if the file is null, return false
     *
     * @param file source file
     * @return if it is a file true, else false
     */
    public static boolean isFile(File file) {
        return (file == null) ? false : file.isFile();
    }

    /**
     * check the two input file is equals<br>
     * if equals, it means to the same file or directory
     *
     * @param file1 file1
     * @param file2 file2
     * @return if equals true, else false
     * @throws IORuntimeException exception
     * @see Files#isSameFile(Path, Path)
     */
    public static boolean equals(File file1, File file2) throws IORuntimeException{
        Assert.notNull(file1);
        Assert.notNull(file2);
        try {
            return Files.isSameFile(file1.toPath(), file2.toPath());
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }


    /**
     * if the file is modified<br>
     * if the file is null or do not exist, return true
     *
     * @param file input file, source file
     * @param lastModifyTime last modify time
     * @return if modified true, else false
     */
    public static boolean isModifed(File file, long lastModifyTime) {
        if (null == file || false == file.exists()) {
            return true;
        }
        return file.lastModified() != lastModifyTime;
    }

    /**
     * normalize the path to provide next operation<br>
     * <ol>
     *     <li>use /</li>
     *     <li>more / trans to one /</li>
     *     <li>trim first and bottom</li>
     *     <li>trans .., . to absolute path</li>
     *     <li>remove prefix like:file:, classpath:</li>
     * </ol>
     *
     * @param source source path
     * @return the normalize String
     */
    public static String normalize(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        String pathToUse = source.replaceAll("[/\\\\]{1,}", "/").trim();

        int prefixIndex = pathToUse.indexOf(StringUtils.COLON);
        String prefix = "";
        if (prefixIndex != -1) {
            prefix = pathToUse.substring(0, prefixIndex + 1);
            if (prefix.contains("/")) {
                prefix = "";
            } else {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            }
        }
        if (pathToUse.startsWith(StringUtils.SLASH)) {
            prefix = prefix + StringUtils.SLASH;
            pathToUse = pathToUse.substring(1);
        }

        List<String> pathList = StringUtils.split(pathToUse, StringUtils.C_SLASH);
        List<String> pathElements = new LinkedList<>();
        int tops = 0;

        for (int i = pathList.size() - 1; i >= 0; i--) {
            String element = pathList.get(i);
            if (StringUtils.DOT.equals(element)) {
                // do nothing
            } else if (StringUtils.DOUBLE_DOT.equals(element)) {
                tops++;
            } else {
                if (tops > 0) {
                    // Merging path element with element corresponding to top path.
                    tops--;
                } else {
                    // Normal path element found.
                    pathElements.add(0, element);
                }
            }
        }

        // Remaining top paths need to be retained.
        for (int i = 0; i < tops; i++) {
            pathElements.add(0, StringUtils.DOUBLE_DOT);
        }

        return prefix + CollectionUtils.join(pathElements, StringUtils.SLASH);
    }


    /**
     * get the sub path of rootDir
     *
     * @param parent the parent or root directory in absolute path
     * @param file the {@link File} to be opened for reading or writing
     * @return the relative sub path
     */
    public static String subPath(String parent, File file) {
        if (StringUtils.isEmpty(parent)) {
            return null;
        }

        String subPath = null;
        try {
            subPath = file.getCanonicalPath();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }

        if (StringUtils.isNotEmpty(parent) && StringUtils.isNotEmpty(subPath)) {
            parent = normalize(parent);
            subPath = normalize(subPath);

            if (subPath != null && StringUtils.startsWith(subPath, subPath, true)) {
                subPath = subPath.substring(parent.length() + 1);
            }
        }
        return subPath;
    }

    /**
     * get the name of File, if is a directory return {@link File#getName()}
     * if is a file return
     *
     * @param file the {@link File} to be opened for reading or writing
     * @return 主文件名
     */
    public static String getName(File file) {
        if (file.isDirectory()) {
            return file.getName();
        }
        return getName(file.getName());
    }

    /**
     * get the name of file object, to deal with extension
     *
     * @param source string of file name
     * @return the name of file
     */
    public static String getName(String source) {
        if (StringUtils.isBlank(source) || false == source.contains(StringUtils.DOT)) {
            return source;
        }
        int toIndex = source.lastIndexOf(StringUtils.DOT);
        return StringUtils.substring(source, 0, toIndex);
    }


    /**
     * get the extension of the file object
     * to deal with .
     *
     * @param file input source
     * @return String ext extension
     */
    public static String getExt(File file) {
        if (null == file) {
            return null;
        }
        if (file.isDirectory()) {
            return null;
        }
        String filename = file.getName();
        int index = filename.lastIndexOf(StringUtils.DOT);
        if (index == -1) {
            return StringUtils.EMPTY;
        } else {
            String ext = filename.substring(index + 1);
            // the ext can not include special char
            return (ext.contains(String.valueOf(StringUtils.C_SLASH))
                    || ext.contains(String.valueOf(StringUtils.C_BACKSLASH)))
                    ? StringUtils.EMPTY : ext;
        }
    }


    /**
     * get the InputStream of source file
     *
     * @param file the {@link File} to be opened for reading or writing
     * @return {@link BufferedInputStream}
     * @throws IORuntimeException exception
     */
    public static BufferedInputStream getInputStream(File file) throws IORuntimeException {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * get the InputStream of source file path
     *
     * @param source the source file path
     * @return {@link BufferedInputStream}
     * @throws IORuntimeException exception
     */
    public static BufferedInputStream getInputStream(String source) throws IORuntimeException {
        return getInputStream(create(source));
    }

    /**
     * get the BOM InputStream
     *
     * @param file the {@link File} to be opened for reading or writing
     * @return {@link UnicodeInputStream} BOM
     * @throws IORuntimeException exception
     */
    public static UnicodeInputStream getUnicodeInputStream(File file) throws IORuntimeException {
        try {
            return new UnicodeInputStream(new FileInputStream(file));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }


    /**
     * get Reader from input file and charset
     *
     * @param file the {@link File} to be opened for reading or writing
     * @param charset charset
     * @return {@link BufferedReader}
     * @throws IORuntimeException exception
     */
    public static BufferedReader getReader(File file, Charset charset) throws IORuntimeException {
        return IoUtils.getReader(getInputStream(file), charset);
    }


    /**
     * get Reader from input file path and charset
     *
     * @param source input source file path
     * @param charset charset
     * @return {@link BufferedReader}
     * @throws IORuntimeException exception
     */
    public static BufferedReader getReader(String source, Charset charset) throws IORuntimeException {
        return getReader(create(source), charset);
    }


    /**
     * read string from file with input charset
     *
     * @param file the {@link File} to be opened for reading or writing
     * @param charset charset
     * @return String
     * @throws IORuntimeException exception
     */
    public static String readTo(File file, Charset charset) throws IORuntimeException {
        InputStream in = null;
        try {
            in = getInputStream(file);
            return IoUtils.readToFast(in, charset);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtils.close(in);
        }
    }


    /**
     * read string from file path with input charset
     *
     * @param source input source file path
     * @param charset charset
     * @return String
     * @throws IORuntimeException exception
     */
    public static String readTo(String source, Charset charset) throws IORuntimeException {
        return readTo(create(source), charset);
    }

    /**
     * read string from file url path with input charset
     *
     * @param url input file url
     * @param charset charset
     * @return String
     * @throws IORuntimeException exception
     */
    public static String readTo(URL url, Charset charset) throws IORuntimeException {
        if (url == null) {
            throw new NullPointerException("Empty url provided!");
        }

        InputStream in = null;
        try {
            in = url.openStream();
            return IoUtils.readToFast(in, charset);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtils.close(in);
        }
    }

    /**
     * get the OutputStream of source file
     *
     * @param file the file to be opened for reading or writing.
     * @param isAppend if <code>true</code>, then bytes will be written
     *                 to the end of the file rather than the beginning
     * @return {@link BufferedOutputStream}
     * @throws IORuntimeException exception
     */
    public static BufferedOutputStream getOutputStream(File file, boolean isAppend) throws IORuntimeException {
        try {
            return new BufferedOutputStream(new FileOutputStream(create(file), isAppend));
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * get the OutputStream of source file
     *
     * @param path the path of {@link File} to be opened for reading or writing
     * @return {@link BufferedOutputStream}
     * @throws IORuntimeException exception
     */
    public static BufferedOutputStream getOutputStream(String path) throws IORuntimeException {
        return getOutputStream(create(path), false);
    }

    /**
     * get Writer of source file with input charset
     *
     * @param file the source file
     * @param charset charset
     * @param isAppend if <code>true</code>, then bytes will be written
     *                 to the end of the file rather than the beginning
     * @return {@link BufferedWriter}
     * @throws IORuntimeException exception
     */
    public static BufferedWriter getWriter(File file, Charset charset,
            boolean isAppend) throws IORuntimeException {
        return IoUtils.getWriter(getOutputStream(file, isAppend), charset);
    }

    /**
     * get Writer of source file with input charset
     *
     * @param source the source file
     * @param charset charset
     * @param isAppend if <code>true</code>, then bytes will be written
     *                 to the end of the file rather than the beginning
     * @return {@link BufferedWriter}
     * @throws IORuntimeException exception
     */
    public static BufferedWriter getWriter(String source, Charset charset,
            boolean isAppend) throws IORuntimeException {
        return getWriter(FileUtils.create(source), charset, isAppend);
    }


    /**
     * get PrintWriter of source file path
     *
     * @param source the source path, absolute path
     * @param charset charset
     * @param isAppend if <code>true</code>, then bytes will be written
     *                 to the end of the file rather than the beginning
     * @return {@link PrintWriter}
     * @throws IORuntimeException exception
     */
    public static PrintWriter getPrinter(String source, String charset,
            boolean isAppend) throws IORuntimeException {
        return new PrintWriter(getWriter(source, CharsetUtils.getCharset(charset), isAppend));
    }

    /**
     * get PrintWriter of source file object
     *
     * @param file the {@link File} to be opened for reading or writing
     * @param charset charset
     * @param isAppend if <code>true</code>, then bytes will be written
     *                 to the end of the file rather than the beginning
     * @return {@link PrintWriter}
     * @throws IORuntimeException exception
     */
    public static PrintWriter getPrinter(File file, String charset, boolean isAppend) throws IORuntimeException {
        return new PrintWriter(getWriter(file, CharsetUtils.getCharset(charset), isAppend));
    }


    /**
     * 将String写入文件，覆盖模式，字符集为UTF-8
     *
     * @param content 写入的内容
     * @param path 文件路径
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File writeUtf8String(String source, String path) throws IORuntimeException {
        return writeString(source, path, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * 将String写入文件，覆盖模式，字符集为UTF-8
     *
     * @param source 写入的内容
     * @param file 文件
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File writeUtf8String(String source, File file) throws IORuntimeException {
        return writeString(source, file, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * 将String写入文件，覆盖模式
     *
     * @param source 写入的内容
     * @param path 文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File writeString(String source, String path, String charset) throws IORuntimeException {
        return writeString(source, touch(path), charset);
    }

    /**
     * 将String写入文件，覆盖模式
     *
     * @param source 写入的内容
     * @param path 文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File writeString(String source, String path, Charset charset) throws IORuntimeException {
        return writeString(source, touch(path), charset);
    }

    /**
     * 将String写入文件，覆盖模式
     *
     *
     * @param source 写入的内容
     * @param file 文件
     * @param charset 字符集
     * @return 被写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File writeString(String source, File file, String charset) throws IORuntimeException {
        return io.linlan.commons.core.io.file.FileWriter.create(file, CharsetUtils.getCharset(charset)).write(source);
    }

    /**
     * 将String写入文件，覆盖模式
     *
     *
     * @param source 写入的内容
     * @param file 文件
     * @param charset 字符集
     * @return 被写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File writeString(String source, File file, Charset charset) throws IORuntimeException {
        return io.linlan.commons.core.io.file.FileWriter.create(file, charset).write(source);
    }

    /**
     * 将String写入文件，追加模式
     *
     * @param source 写入的内容
     * @param path 文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File appendString(String source, String path, String charset) throws IORuntimeException {
        return appendString(source, touch(path), charset);
    }

    /**
     * 将String写入文件，追加模式
     *
     * @param source 写入的内容
     * @param path 文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File appendString(String source, String path, Charset charset) throws IORuntimeException {
        return appendString(source, touch(path), charset);
    }

    /**
     * 将String写入文件，追加模式
     *
     * @param source 写入的内容
     * @param file 文件
     * @param charset 字符集
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File appendString(String source, File file, String charset) throws IORuntimeException {
        return io.linlan.commons.core.io.file.FileWriter.create(file, CharsetUtils.getCharset(charset)).append(source);
    }

    /**
     * 将String写入文件，追加模式
     *
     * @param source 写入的内容
     * @param file 文件
     * @param charset 字符集
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public static File appendString(String source, File file, Charset charset) throws IORuntimeException {
        return FileWriter.create(file, charset).append(source);
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param fullFilePath 文件的全路径，使用POSIX风格
     * @return 文件，若路径为null，返回null
     * @throws IORuntimeException IO异常
     */
    public static File touch(String fullFilePath) throws IORuntimeException {
        if (fullFilePath == null) {
            return null;
        }
        return touch(create(fullFilePath));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param file 文件对象
     * @return 文件，若路径为null，返回null
     * @throws IORuntimeException IO异常
     */
    public static File touch(File file) throws IORuntimeException {
        if (null == file) {
            return null;
        }
        if (false == file.exists()) {
            mkParentDirs(file);
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new IORuntimeException(e);
            }
        }
        return file;
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param parent 父文件对象
     * @param path 文件路径
     * @return File
     * @throws IORuntimeException IO异常
     */
    public static File touch(File parent, String path) throws IORuntimeException {
        return touch(create(parent, path));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param parent 父文件对象
     * @param path 文件路径
     * @return File
     * @throws IORuntimeException IO异常
     */
    public static File touch(String parent, String path) throws IORuntimeException {
        return touch(create(parent, path));
    }

    /**
     * 创建所给文件或目录的父目录
     *
     * @param file 文件或目录
     * @return 父目录
     */
    public static File mkParentDirs(File file) {
        final File parentFile = file.getParentFile();
        if (null != parentFile && false == parentFile.exists()) {
            parentFile.mkdirs();
        }
        return parentFile;
    }


    /**
     * get the readable file size<br>
     *
     * @param size Long the size to be read
     * @return String of read
     */
    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[] {"B", "KB", "MB", "GB", "TB", "EB", "ZB", "YB"};
        int iu = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, iu))
                + " " + units[iu];
    }
}
