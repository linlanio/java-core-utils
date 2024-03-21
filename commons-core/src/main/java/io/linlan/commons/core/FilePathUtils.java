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
package io.linlan.commons.core;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * Filename:FilePathUtils.java
 * Desc:上传对象保存为文件的工具类
 *
 * CreateTime:2020/6/19 14:37
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class FilePathUtils {

    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMM_ddHH = "yyyyMM/ddHH";
    public static final String yyyyMMdd_HHmmss = "yyyyMMdd/HHmmss";


    /** 一级目录的全日期格式 */
    public static final DateFormat DATE_WHOLE_FOLDER = new SimpleDateFormat(yyyyMMdd);
    /** 二级目录月/时格式 */
    public static final DateFormat MONTH_HOUR_FOLDERS = new SimpleDateFormat(yyyyMM_ddHH);
    /** 二级目录日/秒格式 */
    public static final DateFormat DATE_SECOND_FOLDERS = new SimpleDateFormat(yyyyMMdd_HHmmss);

    /** The Constant ILLEGAL_CURRENT_FOLDER_PATTERN. */
    protected static final Pattern ILLEGAL_CURRENT_FOLDER_PATTERN = Pattern
            .compile("^[^/]|[^/]$|/\\.{1,2}|\\\\|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}");


    /** 创建文件名称，通过mode来生成文件名，自动在此方法中区分是否目录分级
     * 在文件名称中，采用62个字符中进行随机取出4个字符组成文件名
     * @param ext   文件扩展名，后缀
     * @param mode  目录模式，0不用目录，文件名称前面采用yyyyMMdd，完整的文件名为：yyyyMMdd****.ext
     *              1：一级目录名yyyyMM/，后面采用天和小时组成ddHH，完整的文件名为：yyyyMM/ddHH****.ext
     *              2：一级目录名yyyyMMdd/，后面采用小时分秒组成HHmmss，完整的文件名为：yyyyMMdd/HHmmss****.ext
     *              7：采用Hash的目录gfs文件分层模式，暂时未实现
     * @return
     */
    private static String genFilename(String ext, int mode) {
        if (mode == 2){
            //包含目录,以天为单位
            return DATE_SECOND_FOLDERS.format(new Date())
                    + RandomUtils.randomCode(4) + "." + ext;
        }else if (mode == 1){
            //包含目录，以月为单位
            return MONTH_HOUR_FOLDERS.format(new Date())
                    + RandomUtils.randomCode(4) + "." + ext;
        }else{
            //不需要目录
            return defaultFilename(ext);
        }
    }

    /** 缺省的文件名路径，直接采用yyyyMMdd****.ext返回
     * @param ext   文件扩展名，后缀
     * @return
     */
    private static String defaultFilename(String ext) {
        //不需要目录
        return DATE_WHOLE_FOLDER.format(new Date())
                + RandomUtils.randomCode(4) + "." + ext;
    }

    /**
     * 文件路径
     * @param prefix 前缀
     * @param ext 后缀
     * @return 返回上传路径
     */
    public static String getPathOfDay(String prefix, String ext) {
        //文件路径
        String path = genFilename(ext, 2);

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }

        return path;
    }

    /**
     * 文件路径
     * @param prefix 前缀
     * @param ext 后缀
     * @return 返回上传路径
     */
    public static String getPathOfMonth(String prefix, String ext) {
        //文件路径
        String path = genFilename(ext, 1);

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }

        return path;
    }

    /**
     * Generate filename.
     *
     * @param prefix
     *            the prefix
     * @param ext
     *            the ext
     * @return the string
     */
    public static String getPathNoFolder(String prefix, String ext) {
        //文件路径
        String path = genFilename(ext, 0);

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }
        return path;
    }

    /**
     * Generate by filename.
     *
     * @param prefix
     *            the path
     * @param filename
     *            the file name
     * @param ext
     *            the ext
     * @return the string
     */
    public static String getByFilename(String prefix, String filename, String ext) {
        //处理文件名
        String name = sanitizeFileName(filename);
        String path = name + "." + ext;
        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }
        //检查路径
        if (isSingleExtension(ext)){
            if (isValidPath(path)){
                return path;
            }
        }
        return defaultFilename(ext);
    }

    /**
     * Sanitizes a filename from certain chars.<br />
     *
     * This method enforces the <code>forceSingleExtension</code> property and
     * then replaces all occurrences of \, /, |, :, ?, *, &quot;, &lt;, &gt;,
     * control chars by _ (underscore).
     *
     * @param filename
     *            a potentially 'malicious' filename
     * @return sanitized filename
     */
    public static String sanitizeFileName(final String filename) {

        if (StringUtils.isBlank(filename))
            return filename;

        String name = forceSingleExtension(filename);

        // Remove \ / | : ? * " < > 'Control Chars' with _
        return name.replaceAll("\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
    }

    /**
     * Sanitizes a folder name from certain chars.<br />
     *
     * This method replaces all occurrences of \, /, |, :, ?, *, &quot;, &lt;,
     * &gt;, control chars by _ (underscore).
     *
     * @param folderName
     *            a potentially 'malicious' folder name
     * @return sanitized folder name
     */
    public static String sanitizeFolderName(final String folderName) {

        if (StringUtils.isBlank(folderName))
            return folderName;

        // Remove . \ / | : ? * " < > 'Control Chars' with _
        return folderName.replaceAll(
                "\\.|\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
    }

    /**
     * Checks whether a path complies with the FCKeditor File Browser <a href="http://docs.fckeditor.net/FCKeditor_2.x/Developers_Guide/Server_Side_Integration#File_Browser_Requests"
     * target="_blank">rules</a>.
     *
     * @param path
     *            a potentially 'malicious' path
     * @return <code>true</code> if path complies with the rules, else
     *         <code>false</code>
     */
    public static boolean isValidPath(final String path) {
        if (StringUtils.isBlank(path))
            return false;

        if (ILLEGAL_CURRENT_FOLDER_PATTERN.matcher(path).find())
            return false;

        return true;
    }

    /**
     * Replaces all dots in a filename with underscores except the last one.
     *
     * @param filename
     *            filename to sanitize
     * @return string with a single dot only
     */
    public static String forceSingleExtension(final String filename) {
        return filename.replaceAll("\\.(?![^.]+$)", "_");
    }

    /**
     * Checks if a filename contains more than one dot.
     *
     * @param filename
     *            filename to check
     * @return <code>true</code> if filename contains severals dots, else
     *         <code>false</code>
     */
    public static boolean isSingleExtension(final String filename) {
        return filename.matches("[^\\.]+\\.[^\\.]+");
    }

    /**
     * Checks a directory for existence and creates it if non-existent.
     *
     * @param dir
     *            directory to check/create
     */
    public static void checkDirAndCreate(File dir) {
        if (!dir.exists())
            dir.mkdirs();
    }

//    /**
//     * Iterates over a basman name and returns the first non-existent file.<br />
//     * This method extracts a file's basman name, iterates over it until the first
//     * non-existent appearance with <code>basename(n).ext</code>. Where n is a
//     * positive integer starting from one.
//     *
//     * @param file
//     *            basman file
//     * @return first non-existent file
//     */
//    public static File getUniqueFile(final File file) {
//        if (!file.exists())
//            return file;
//
//        File tmpFile = new File(file.getAbsolutePath());
//        File parentDir = tmpFile.getParentFile();
//        int count = 1;
//        String extension = FilenameUtils.getExtension(tmpFile.getName());
//        String baseName = FilenameUtils.getBaseName(tmpFile.getName());
//        do {
//            tmpFile = new File(parentDir, baseName + "(" + count++ + ")."
//                    + extension);
//        } while (tmpFile.exists());
//        return tmpFile;
//    }

    /**
     * Gets the files.扫描目录获取文件名
     *
     * @param folder
     *            the folder
     * @return the files
     */
    public static List<File> getFiles(File folder){
        List<File>files=new ArrayList<File>();
        iterateFolder(folder, files);
        return files;
    }

    /**
     * Iterate folder.重复扫描目录，将文件名存入列表对象
     *
     * @param folder
     *            the folder
     * @param files
     *            the files
     */
    private static void iterateFolder(File folder,List<File>files)  {
        File flist[] = folder.listFiles();
        files.add(folder);
        if (flist == null || flist.length == 0) {
            files.add(folder) ;
        }else{
            for (File f : flist) {
                if (f.isDirectory()) {
                    iterateFolder(f,files);
                } else {
                    files.add(f) ;
                }
            }
        }
    }

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        System.out.println(genFilename("gif", 0));
        System.out.println(getPathOfDay("/howai", "gif"));
        System.out.println(getPathOfMonth("/linlan", "jpg"));
        System.out.println(getPathNoFolder("/cdn", "doc"));

    }

}
