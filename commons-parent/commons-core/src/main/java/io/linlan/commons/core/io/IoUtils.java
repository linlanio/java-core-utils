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
package io.linlan.commons.core.io;


import io.linlan.commons.core.HexUtils;
import io.linlan.commons.core.StringUtils;
import io.linlan.commons.core.io.file.FastByteBuffer;
import io.linlan.commons.core.io.file.FastOutputStream;
import io.linlan.commons.core.io.file.FileWriter;
import io.linlan.commons.core.io.file.IStreamStatus;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * IO class to provide utils for use
 * Filename:IoUtils.java
 * Desc:IO utils to deal with stream, and provide methods such as:
 * copy, read, close, write etc.
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * Createtime 2017/7/12 10:58 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class IoUtils
{
    /**
     * constructor of self
     */
    private IoUtils() {}

    /**
     * default buffer size
     */
    public static final int DEFAULT_BUFFER_SIZE = FastByteBuffer.DEFAULT_BUFFER_SIZE;
    /**
     * default large buffer size
     */
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 4096;
    /** end of file */
    public static final int EOF = -1;

    /**
     * copy from Reader to Writer, use the default buffer size
     *
     * @param reader Reader
     * @param writer Writer
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copy(Reader reader, Writer writer) throws IOException {
        return copy(reader, writer, DEFAULT_BUFFER_SIZE);
    }

    /**
     * copy from Reader to Writer, use the default buffer size
     *
     * @param reader Reader
     * @param writer Writer
     * @param bufferSize input buffer size
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copy(Reader reader, Writer writer, int bufferSize) throws IOException {
        return copy(reader, writer, bufferSize, null);
    }

    /**
     * copy from Reader to Writer, use the default buffer size
     *
     * @param reader Reader
     * @param writer Writer
     * @param bufferSize input buffer size
     * @param status the status of operation
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copy(Reader reader, Writer writer, int bufferSize, IStreamStatus status) throws IOException {
        char[] buffer = new char[bufferSize];
        long size = 0;
        int readSize;
        if (null != status) {
            status.start();
        }
        while ((readSize = reader.read(buffer, 0, bufferSize)) != EOF) {
            writer.write(buffer, 0, readSize);
            size += readSize;
            writer.flush();
            if (null != status) {
                status.progress(size);
            }
        }
        if (null != status) {
            status.finish();
        }
        return size;
    }

    /**
     * copy from in to out, use default buffer size
     *
     * @param in InputStream
     * @param out OutputStream
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copy(InputStream in, OutputStream out) throws IOException {
        return copy(in, out, DEFAULT_BUFFER_SIZE);
    }

    /**
     * copy from in to out, use input buffer size
     *
     * @param in InputStream
     * @param out OutputStream
     * @param bufferSize input buffer size
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
        return copy(in, out, bufferSize, null);
    }

    /**
     * copy from in to out, use input buffer size
     *
     * @param in InputStream
     * @param out OutputStream
     * @param bufferSize input buffer size
     * @param status stream status
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copy(InputStream in, OutputStream out, int bufferSize, IStreamStatus status) throws IOException {
        if (null == in) {
            throw new NullPointerException("InputStream is null!");
        }
        if (null == out) {
            throw new NullPointerException("OutputStream is null!");
        }
        if (bufferSize <= 0) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }

        byte[] buffer = new byte[bufferSize];
        long size = 0;
        if (null != status) {
            status.start();
        }
        for (int readSize = -1; (readSize = in.read(buffer)) != EOF;) {
            out.write(buffer, 0, readSize);
            size += readSize;
            out.flush();
            if (null != status) {
                status.progress(size);
            }
        }
        if (null != status) {
            status.finish();
        }
        return size;
    }

    /**
     * copy from in to out, use input buffer size, in NIO mode, new IO
     *
     * @param in InputStream
     * @param out OutputStream
     * @param bufferSize input buffer size
     * @param status stream status
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copyNIO(InputStream in, OutputStream out, int bufferSize, IStreamStatus status) throws IOException {
        return copy(Channels.newChannel(in), Channels.newChannel(out), bufferSize, status);
    }

    /**
     * use NIO to copy file stream from in to out
     *
     * @param in FileInputStream
     * @param out FileOutputStream
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copy(FileInputStream in, FileOutputStream out) throws IOException {
        if (null == in) {
            throw new NullPointerException("FileInputStream is null!");
        }
        if (null == out) {
            throw new NullPointerException("FileOutputStream is null!");
        }

        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();

        return inChannel.transferTo(0, inChannel.size(), outChannel);
    }

    /**
     * use NIO to copy file stream from in to out, the operation will not close the stream
     *
     * @param in {@link ReadableByteChannel}
     * @param out {@link WritableByteChannel}
     * @param bufferSize input buffer size
     * @param status {@link IStreamStatus} the status of Stream operation
     * @return the copy result of long stream
     * @throws IOException exception
     */
    public static long copy(ReadableByteChannel in, WritableByteChannel out, int bufferSize, IStreamStatus status) throws IOException {
        if (null == in) {
            throw new NullPointerException("In is null!");
        }
        if (null == out) {
            throw new NullPointerException("Out is null!");
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize <= 0 ? DEFAULT_BUFFER_SIZE : bufferSize);
        long size = 0;
        if (null != status) {
            status.start();
        }
        while (in.read(byteBuffer) != EOF) {
            byteBuffer.flip();//写转读
            size += out.write(byteBuffer);
            byteBuffer.clear();
            if (null != status) {
                status.progress(size);
            }
        }
        if (null != status) {
            status.finish();
        }

        return size;
    }



    /**
     * get Reader from in with input charset
     *
     * @param in InputStream
     * @param charset charset
     * @return {@link BufferedReader}
     */
    public static BufferedReader getReader(InputStream in, Charset charset) {
        if (null == in) {
            return null;
        }

        InputStreamReader reader = null;
        if (null == charset) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, charset);
        }

        return new BufferedReader(reader);
    }


    /**
     * get Writer from out with input charset
     *
     * @param out OutputStream
     * @param charset charset
     * @return {@link BufferedReader}
     */
    public static BufferedWriter getWriter(OutputStream out, Charset charset){
        if(null == out){
            return null;
        }
        OutputStreamWriter osw;
        if(null == charset){
            osw = new OutputStreamWriter(out);
        }else{
            osw = new OutputStreamWriter(out, charset);
        }
        return new BufferedWriter(osw);
    }

    /**
     * read from in with input charset
     *
     * @param in {@link InputStream}
     * @param charset charset
     * @return the String of in stream in FastOutputStream
     * @throws IOException exception
     */
    public static String readToFast(InputStream in, Charset charset) throws IOException {
        FastOutputStream out = readToFast(in);
        return null == charset ? out.toString() : out.toString(charset);
    }

    /**
     * readToFast from in with input charset
     *
     * @param in {@link InputStream}
     * @return the String of in stream in FastOutputStream
     * @throws IOException exception
     */
    private static FastOutputStream readToFast(InputStream in) throws IOException {
        final FastOutputStream out = new FastOutputStream();
        copy(in, out);
        return out;
    }

    /**
     * read stream from Reader in String
     *
     * @param reader Reader
     * @return the String of Reader stream
     * @throws IOException exception
     */
    public static String read(Reader reader) throws IOException {
        final StringBuilder builder = StringUtils.builder();
        final CharBuffer buffer = CharBuffer.allocate(DEFAULT_BUFFER_SIZE);
        while (-1 != reader.read(buffer)) {
            builder.append(buffer.flip().toString());
        }
        return builder.toString();
    }

    /**
     * read stream from FileChannel in String
     *
     * @param fileChannel {@link FileChannel}
     * @param charset charset
     * @return the String of FileChannel stream
     * @throws IOException exception
     */
    public static String read(FileChannel fileChannel, Charset charset) throws IOException {
        final MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size()).load();
        return StringUtils.getString(buffer, charset);
    }

    /**
     * read stream from in to byte
     *
     * @param in {@link InputStream}
     * @return byte[]
     * @throws IOException exception
     */
    public static byte[] readToByte(InputStream in) throws IOException {
        FastOutputStream out = new FastOutputStream();
        copy(in, out);
        return out.toByte();
    }

    /**
     * read stream from in to byte with the input length
     *
     * @param in {@link InputStream}
     * @param length length of in to read
     * @return byte[]
     * @throws IOException exception
     */
    public static byte[] readToByte(InputStream in, int length) throws IOException {
        byte[] b = new byte[length];
        int readLength = in.read(b);
        if(readLength < length){
            byte[] b2 = new byte[length];
            System.arraycopy(b, 0, b2, 0, readLength);
            return b2;
        }else{
            return b;
        }
    }

    /**
     * read stream from in with input length and lower case, the result is Hex string
     *
     * @param in {@link InputStream}
     * @param length the length of in to read
     * @param toLowerCase true to lower case ， false to upper case
     * @return String in Hex
     * @throws IOException exception
     */
    public static String readToHex(InputStream in, int length, boolean toLowerCase) throws IOException {
        return HexUtils.encodeHexStr(readToByte(in, length), toLowerCase);
    }


    /**
     * read object from in
     *
     * @param in {@link InputStream}
     * @param <T> the type of object
     * @return T out put is Object
     * @throws IOException exception
     */
    public static <T> T readToObj(InputStream in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(in);
            // may fail with CCE if serialised form is incorrect
            final T obj = (T) ois.readObject();
            return obj;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }


    /**
     * read String source to ByteArrayInputStream
     *
     * @param source source string
     * @param charset charset
     * @return {@link ByteArrayInputStream} to InputStream
     */
    public static ByteArrayInputStream readToStream(String source, Charset charset) {
        if (source == null) {
            return null;
        }
        return new ByteArrayInputStream(StringUtils.getBytes(source, charset));
    }


    /**
     * write the input byte[] source to out OutputStream
     *
     * @param out OutputStream
     * @param isCloseOut if close while write over
     * @param source the input byte[] source
     * @throws IOException exception
     */
    public static void write(OutputStream out, boolean isCloseOut, byte[] source) throws IOException {
        try {
            out.write(source);
        } finally {
            if (isCloseOut) {
                close(out);
            }
        }
    }

    /**
     * write the input byte[] source to out FastOutputStream
     *
     * @param out FastOutputStream
     * @param isCloseOut if close while write over
     * @param source the input byte[] source
     * @throws IOException exception
     */
    public static void write(FastOutputStream out, boolean isCloseOut, byte[] source) throws IOException {
        try {
            out.write(source);
        } finally {
            if (isCloseOut) {
                close(out);
            }
        }
    }

    /**
     * write the input objects source to out OutputStream
     *
     * @param out OutputStream
     * @param charset charset
     * @param isCloseOut if close while write over
     * @param source the input source objects
     * @throws IOException exception
     */
    public static void write(OutputStream out, Charset charset, boolean isCloseOut,
            Object... source) throws IOException {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(out, charset);
            for (Object object : source) {
                if (object != null) {
                    osw.write(object.toString());
                    osw.flush();
                }
            }
        } catch (Exception e) {
            throw new IOException("Write source to OutputStream error!", e);
        } finally {
            if (isCloseOut) {
                close(osw);
            }
        }
    }

    /**
     * close the Closeable
     *
     * @param closeable {@link Closeable}
     */
    public static void close(Closeable closeable) {
        if(null != closeable){
            try {
                closeable.close();
            } catch (Exception e) {
                //silent do nothing
            }
        }
    }

    /**
     * close the AutoCloseable
     *
     * @param closeable {@link AutoCloseable}
     */
    public static void close(AutoCloseable closeable) {
        if (null != closeable){
            try {
                closeable.close();
            } catch (Exception e) {
                //silent do nothing
            }
        }
    }

    /**
     * 读取文件所有数据<br>
     * 文件的长度不能超过Integer.MAX_VALUE
     *
     * @param file 文件
     * @return 字节码
     * @throws IORuntimeException IO异常
     */
    public static byte[] readBytes(File file) throws IORuntimeException {
        return io.linlan.commons.core.io.file.FileReader.create(file).readBytes();
    }

    /**
     * 读取文件所有数据<br>
     * 文件的长度不能超过Integer.MAX_VALUE
     *
     * @param filePath 文件路径
     * @return 字节码
     * @throws IORuntimeException IO异常
     * @since 3.2.0
     */
    public static byte[] readBytes(String filePath) throws IORuntimeException {
        return readBytes(FileUtils.create(filePath));
    }

    /**
     * 从流中读取bytes，读取完毕后关闭流
     *
     * @param in {@link InputStream}
     * @return bytes
     * @throws IORuntimeException IO异常
     */
    public static byte[] readBytes(InputStream in) throws IORuntimeException {
        return readBytes(in, true);
    }

    /**
     * 从流中读取bytes
     *
     * @param in            {@link InputStream}
     * @param isCloseStream 是否关闭输入流
     * @return bytes
     * @throws IORuntimeException IO异常
     * @since 5.0.4
     */
    public static byte[] readBytes(InputStream in, boolean isCloseStream) throws IORuntimeException {
        final FastOutputStream out = new FastOutputStream();
        try {
            IoUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isCloseStream) {
            IoUtils.close(in);
        }
        return out.toByte();
    }


    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @param path 目标文件
     * @return 目标文件
     * @throws IORuntimeException IO异常
     */
    public static File writeBytes(byte[] data, String path) throws IORuntimeException {
        return writeBytes(data, FileUtils.touch(path));
    }

    /**
     * 写数据到文件中
     *
     * @param dest 目标文件
     * @param data 数据
     * @return 目标文件
     * @throws IORuntimeException IO异常
     */
    public static File writeBytes(byte[] data, File dest) throws IORuntimeException {
        return writeBytes(data, dest, 0, data.length, false);
    }

    /**
     * 写入数据到文件
     *
     * @param data     数据
     * @param dest     目标文件
     * @param off      数据开始位置
     * @param len      数据长度
     * @param isAppend 是否追加模式
     * @return 目标文件
     * @throws IORuntimeException IO异常
     */
    public static File writeBytes(byte[] data, File dest, int off, int len, boolean isAppend) throws IORuntimeException {
        return FileWriter.create(dest).write(data, off, len, isAppend);
    }

    /**
     * 将{@link InputStream}转换为支持mark标记的流<br>
     * 若原流支持mark标记，则返回原流，否则使用{@link BufferedInputStream} 包装之
     *
     * @param in 流
     * @return {@link InputStream}
     * @since 4.0.9
     */
    public static InputStream toMarkSupportStream(InputStream in) {
        if (null == in) {
            return null;
        }
        if (false == in.markSupported()) {
            return new BufferedInputStream(in);
        }
        return in;
    }
    
}
