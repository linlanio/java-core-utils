/**
 * Copyright 2015 the original author or Linlan authors.
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

import io.linlan.commons.core.io.FileUtils;
import io.linlan.commons.core.io.IoUtils;
import io.linlan.commons.core.io.file.FastOutputStream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Zip class to provide utils for use
 * Filename:ZipUtils.java
 * Desc:Zip utils include zip, unzip, gzip methods
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * Createtime 2017/7/11 9:58 PM
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public final class ZipUtils {
    /**
     * constructor of self
     */
    private ZipUtils() {

    }

    /**
     * zip the file in the source path to a zip file
     *
     * @param srcPath source path
     * @return {@link File} a zip file
     * @throws CoreException IO Exception
     */
    public static File zip(String srcPath) throws
            CoreException
    {
        return zip(FileUtils.create(srcPath));
    }

    /**
     * zip the file in the source path to a zip file
     *
     * @param srcFile source path
     * @return {@link File} a zip file
     * @throws CoreException IO Exception
     */
    public static File zip(File srcFile) throws
            CoreException
    {
        File zipFile = FileUtils.create(srcFile.getParentFile(), FileUtils.getName(srcFile) + ".zip");
        zip(zipFile, false, srcFile);
        return zipFile;
    }

    /**
     * zip the file or file in a folder<br>
     * do not include the root folder
     *
     * @param srcPath source path, if is a file the path is whole,
     *                if is a folder the path is the root folder's path
     * @param zipPath the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @return {@link File} a zip file
     * @throws CoreException IO Exception
     */
    public static File zip(String srcPath, String zipPath) throws
            CoreException
    {
        return zip(srcPath, zipPath, false);
    }

    /**
     * zip the file or file in a folder<br>
     *
     * @param srcPath source path, if is a file the path is whole,
     *                if is a folder the path is the root folder's path
     * @param zipPath the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @param withSrcDir if include the root folder, if true include, else false
     * @return {@link File} a zip file
     * @throws CoreException IO Exception
     */
    public static File zip(String srcPath, String zipPath, boolean withSrcDir) throws
            CoreException
    {
        File srcFile = FileUtils.create(srcPath);
        File zipFile = FileUtils.create(zipPath);
        zip(zipFile, withSrcDir, srcFile);
        return zipFile;
    }

    /**
     * zip the file or file in a folder<br>
     *
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @param withSrcDir if include the root folder, if true include, else false
     * @param srcFiles all the files in folder to be zipped
     * @return {@link File} a zip file
     * @throws CoreException IO Exception
     */
    public static File zip(File zipFile, boolean withSrcDir, File... srcFiles) throws
            CoreException
    {
        validateFiles(zipFile, srcFiles);

        ZipOutputStream out = null;
        try {
            out = getZipOutputStream(zipFile);
            for (File srcFile : srcFiles) {
                // 如果只是压缩一个文件，则需要截取该文件的父目录
                String srcRootDir = srcFile.getCanonicalPath();
                if (srcFile.isFile() || withSrcDir) {
                    srcRootDir = srcFile.getParent();
                }
                // 调用递归压缩方法进行目录或文件压缩
                zip(out, srcRootDir, srcFile);
                out.flush();
            }
        } catch (IOException e) {
            throw new CoreException(e);
        } finally {
            IoUtils.close(out);
        }
        return zipFile;
    }

    /**
     * zip the stream info to a zip file, use the input charset<br>
     *
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @param path the path or the name of stream in zip file
     * @param data data to zip
     * @param charset charset input
     * @return {@link File} a zip file
     * @throws CoreException IO Exception
     */
    public static File zip(File zipFile, String path, String data, Charset charset) throws
            CoreException
    {
        return zip(zipFile, path, IoUtils.readToStream(data, charset));
    }

    /**
     * zip the input stream to a zip file<br>
     *
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @param path the path or the name of stream in zip file
     * @param in input stream to be zipped
     * @return {@link File} a zip file
     * @throws CoreException IO Exception
     * @since 3.0.6
     */
    public static File zip(File zipFile, String path, InputStream in) throws
            CoreException
    {
        ZipOutputStream out = null;
        try {
            out = getZipOutputStream(zipFile);
            zip(out, path, in);
        } finally {
            IoUtils.close(out);
        }
        return zipFile;
    }

    /**
     * get {@link ZipOutputStream}
     *
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @return {@link ZipOutputStream}
     */
    private static ZipOutputStream getZipOutputStream(File zipFile) {
        return new ZipOutputStream(new CheckedOutputStream(FileUtils.getOutputStream(zipFile, false), new CRC32()));
    }

    /**
     * to zip all the file in the file folder or path
     *
     * @param out zip output stream
     * @param srcRootDir source root directory
     * @param file file object, recursion to get
     * @throws CoreException IO Exception
     */
    private static void zip(ZipOutputStream out, String srcRootDir, File file) throws
            CoreException
    {
        if (file == null) {
            return;
        }
        // if a file, zip
        if (file.isFile()) {
            // get the relative path to zip file
            final String subPath = FileUtils.subPath(srcRootDir, file);
            BufferedInputStream in = null;
            try {
                in = FileUtils.getInputStream(file);
                zip(out, subPath, in);
            } finally {
                IoUtils.close(in);
            }
        } else {
            // if a folder, zip file or folder in this
            for (File childFile : file.listFiles()) {
                zip(out, srcRootDir, childFile);
            }
        }
    }

    /**
     * recursion to get file or sub folder from input stream to zip, out in zip output stream
     *
     * @param out ZipOutputStream
     * @param path the path
     * @param in InputStream
     * @throws CoreException IO Exception
     */
    private static void zip(ZipOutputStream out, String path, InputStream in) throws
            CoreException
    {
        try {
            out.putNextEntry(new ZipEntry(path));
            IoUtils.copy(in, out);
        } catch (IOException e) {
            throw new CoreException(e);
        } finally {
            closeEntry(out);
        }
    }


    /**
     * validate the file or folder in the input file path, the path can be sub folder in recursion
     *
     * @param zipFile a zip file
     * @param srcFiles the source file list
     */
    private static void validateFiles(File zipFile, File... srcFiles) throws
            CoreException
    {
        for (File srcFile : srcFiles) {
            if (false == srcFile.exists()) {
                throw new CoreException(StringUtils.format("File [{}] not exist!", srcFile.getAbsolutePath()));
            }

            try {
                // the zip file can not be the same path with file to be zipped
                if (srcFile.isDirectory() && zipFile.getParent().contains(srcFile.getCanonicalPath())) {
                    throw new CoreException("[zipPath] must not be the child directory of [srcPath]!");
                }

                if (false == zipFile.exists()) {
                    FileUtils.create(zipFile);
                }
            } catch (IOException e) {
                throw new CoreException(e);
            }
        }
    }

    /**
     * close the present entry, go to next entry
     *
     * @param out ZipOutputStream
     */
    private static void closeEntry(ZipOutputStream out) {
        try {
            out.closeEntry();
        } catch (IOException e) {
        }
    }

    /**
     * unzip a zip file to the path before
     *
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @return File the path is in the zip file when it is be zipped
     * @throws CoreException IO Exception
     */
    public static File unzip(File zipFile) throws
            CoreException
    {
        return unzip(zipFile, FileUtils.create(zipFile.getParentFile(), FileUtils.getName(zipFile)));
    }

    /**
     * unzip a zip file by the zip path before
     *
     * @param zipFilePath zip file path
     * @return File
     * @throws CoreException IO Exception
     */
    public static File unzip(String zipFilePath) throws
            CoreException
    {
        return unzip(FileUtils.create(zipFilePath));
    }

    /**
     * unzip a zip file to the out file directory
     *
     * @param zipFilePath zip file path
     * @param outFileDir out file directory
     * @return File
     * @throws CoreException IO Exception
     */
    public static File unzip(String zipFilePath, String outFileDir) throws
            CoreException
    {
        return unzip(FileUtils.create(zipFilePath), FileUtils.mkdirs(outFileDir));
    }

    /**
     * unzip a zip file to out file
     *
     * @param zipFile zip file
     * @param outFile out file
     * @return File
     * @throws CoreException IO Exception
     */
    public static File unzip(File zipFile, File outFile) throws
            CoreException
    {
        ZipFile zipFileObj = null;
        try {
            zipFileObj = new ZipFile(zipFile);
            final Enumeration<ZipEntry> em = (Enumeration<ZipEntry>) zipFileObj.entries();
            ZipEntry zipEntry = null;
            File outItemFile = null;
            while (em.hasMoreElements()) {
                zipEntry = em.nextElement();
                outItemFile = new File(outFile, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    outItemFile.mkdirs();
                } else {
                    FileUtils.create(outItemFile);
                    copy(zipFileObj, zipEntry, outItemFile);
                }
            }
        } catch (IOException e) {
            throw new CoreException(e);
        } finally {
            IoUtils.close(zipFileObj);

        }
        return outFile;
    }


    /**
     * copy from a zip file in stream mode
     *
     * @param zipFile a zip file
     * @param zipEntry zip entry
     * @param outItemFile out item file
     * @throws IOException IO exception
     */
    private static void copy(ZipFile zipFile, ZipEntry zipEntry, File outItemFile) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = zipFile.getInputStream(zipEntry);
            out = FileUtils.getOutputStream(outItemFile, false);
            IoUtils.copy(in, out);
        } finally {
            IoUtils.close(out);
            IoUtils.close(in);
        }
    }

    /**
     * gzip to use gzip utils, gzip source content
     *
     * @param source the source to be zipped
     * @param charset Charset
     * @return byte[] zipped
     * @throws CoreException IO Exception
     */
    public static byte[] gzip(String source, Charset charset) throws
            CoreException
    {
        return gzip(StringUtils.getBytes(source, charset));
    }

    /**
     * Gzip
     *
     * @param source the source byte to be zipped
     * @return byte[] zipped
     * @throws CoreException IO Exception
     */
    public static byte[] gzip(byte[] source) throws
            CoreException
    {
        FastOutputStream bos = new FastOutputStream(source.length);
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(bos);
            gos.write(source, 0, source.length);
            gos.finish();
            gos.flush();
            source = bos.toByte();
        } catch (IOException e) {
            throw new CoreException(e);
        } finally {
            IoUtils.close(gos);
        }
        return source;
    }

    /**
     * Gzip a zip file to byte
     *
     * @param file the file to be zipped
     * @return byte[] zipped
     * @throws CoreException IO Exception
     */
    public static byte[] gzip(File file) throws
            CoreException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        GZIPOutputStream gos = null;
        BufferedInputStream in;
        try {
            gos = new GZIPOutputStream(bos);
            in = FileUtils.getInputStream(file);
            IoUtils.copy(in, gos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new CoreException(e);
        } finally {
            IoUtils.close(gos);
        }
    }

    /**
     * unGzip source byte to normal byte with input charset
     *
     * @param source the zipped byte stream
     * @param charset Charset
     * @return String unzip to normal byte stream
     * @throws CoreException IO Exception
     */
    public static String unGzip(byte[] source, Charset charset) throws
            CoreException
    {
        return StringUtils.getString(unGzip(source), charset);
    }

    /**
     * unGzip zipped byte to normal byte
     *
     * @param source source byte buffer
     * @return getBytes
     * @throws CoreException IO Exception
     */
    public static byte[] unGzip(byte[] source) throws
            CoreException
    {
        GZIPInputStream gzi = null;
        ByteArrayOutputStream bos = null;
        try {
            gzi = new GZIPInputStream(new ByteArrayInputStream(source));
            bos = new ByteArrayOutputStream(source.length);
            IoUtils.copy(gzi, bos);
            source = bos.toByteArray();
        } catch (IOException e) {
            throw new CoreException(e);
        } finally {
            IoUtils.close(gzi);
        }
        return source;
    }

}