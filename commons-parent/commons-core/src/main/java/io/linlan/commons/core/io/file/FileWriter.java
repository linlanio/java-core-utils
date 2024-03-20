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
package io.linlan.commons.core.io.file;


import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import io.linlan.commons.core.CharsetUtils;
import io.linlan.commons.core.lang.Assert;
import io.linlan.commons.core.io.FileUtils;
import io.linlan.commons.core.io.IORuntimeException;
import io.linlan.commons.core.io.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * File Writer to write file
 * Filename:FileWriter.java
 * Desc: FileWriter to write from string, stream, etc.
 *
 * CreateTime:2020-08-25 10:47 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class FileWriter extends FileWrapper {

    /**
     * constructor of self with input file, default charset
     * @param file the file
     */
    public FileWriter(File file) {
        this(file, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * constructor of self with input file, default charset
     * @param path the file path
     */
    public FileWriter(String path) {
        this(path, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * constructor of self with input file, charset
     * @param file the file
     * @param charset the charset
     */
    public FileWriter(File file, Charset charset) {
        super(file, charset);
        checkFile();
    }

    /**
     * constructor of self with input 
     * @param path the path, if relative, the file will be saved
     *             with relative path of ClassPath
     * @param charset the charset
     */
    public FileWriter(String path, Charset charset) {
        this(FileUtils.create(path), charset);
    }


    /**
     * create FileWriter with input file, charset
     * @param file the file
     * @param charset the charset
     * @return {@link FileWriter}
     */
    public static FileWriter create(File file, Charset charset){
        return new FileWriter(file, charset);
    }

    /**
     * create FileWriter with input file, default charset
     * @param file the file
     * @return {@link FileWriter}
     */
    public static FileWriter create(File file){
        return new FileWriter(file);
    }
    
    /**
     * write String to a file with append mode
     * 
     * @param source the source content
     * @param isAppend if true then append, else
     * @return File
     * @throws IORuntimeException IO异常
     */
    public File write(String source, boolean isAppend) throws IORuntimeException {
        BufferedWriter writer = null;
        try {
            writer = getWriter(isAppend);
            writer.write(source);
            writer.flush();
        }catch(IOException e){
            throw new IORuntimeException(e);
        }finally {
            IoUtils.close(writer);
        }
        return file;
    }
    
    /**
     * 将String写入文件，覆盖模式
     * 
     * @param source 写入的内容
     * @return File
     * @throws IORuntimeException IO异常
     */
    public File write(String source) throws IORuntimeException {
        return write(source, false);
    }

    /**
     * 将String写入文件，追加模式
     * 
     * @param source 写入的内容
     * @return 写入的文件
     * @throws IORuntimeException IO异常
     */
    public File append(String source) throws IORuntimeException {
        return write(source, true);
    }

    /**
     * 将列表写入文件，覆盖模式
     * 
     * @param <T> 集合元素类型
     * @param list 列表
     * @return File
     * @throws IORuntimeException IO异常
     */
    public <T> File writeLines(Collection<T> list) throws IORuntimeException {
        return writeLines(list, false);
    }

    /**
     * 将列表写入文件，追加模式
     * 
     * @param <T> 集合元素类型
     * @param list 列表
     * @return File
     * @throws IORuntimeException IO异常
     */
    public <T> File appendLines(Collection<T> list) throws IORuntimeException {
        return writeLines(list, true);
    }

    /**
     * 将列表写入文件
     * 
     * @param <T> 集合元素类型
     * @param list 列表
     * @param isAppend 是否追加
     * @return File
     * @throws IORuntimeException IO异常
     */
    public <T> File writeLines(Collection<T> list, boolean isAppend) throws IORuntimeException {
        return writeLines(list, null, isAppend);
    }
    
    /**
     * 将列表写入文件
     * 
     * @param <T> 集合元素类型
     * @param list 列表
     * @param isAppend 是否追加
     * @return File
     * @throws IORuntimeException IO异常
     * @since 3.1.0
     */
    public <T> File writeLines(Collection<T> list, LineSeparator lineSeparator, boolean isAppend) throws IORuntimeException {
        PrintWriter writer = null;
        try {
            writer = getPrintWriter(isAppend);
            for (T t : list) {
                if (t != null) {
                    if(null == lineSeparator) {
                        //默认换行符
                        writer.println(t.toString());
                    }else {
                        //自定义换行符
                        writer.print(t.toString());
                        writer.print(lineSeparator);
                    }
                    writer.flush();
                }
            }
        } finally {
            IoUtils.close(writer);
        }
        return this.file;
    }
    
    /**
     * 写入数据到文件
     * 
     * @param data 数据
     * @param off 数据开始位置
     * @param len 数据长度
     * @return File
     * @throws IORuntimeException IO异常
     */
    public File write(byte[] data, int off, int len) throws IORuntimeException {
        return write(data, off, len, false);
    }
    
    /**
     * 追加数据到文件
     * 
     * @param data 数据
     * @param off 数据开始位置
     * @param len 数据长度
     * @return File
     * @throws IORuntimeException IO异常
     */
    public File append(byte[] data, int off, int len) throws IORuntimeException {
        return write(data, off, len, true);
    }

    /**
     * 写入数据到文件
     * 
     * @param data 数据
     * @param off 数据开始位置
     * @param len 数据长度
     * @param isAppend 是否追加模式
     * @return File
     * @throws IORuntimeException IO异常
     */
    public File write(byte[] data, int off, int len, boolean isAppend) throws IORuntimeException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(FileUtils.touch(file), isAppend);
            out.write(data, off, len);
            out.flush();
        }catch(IOException e){
            throw new IORuntimeException(e);
        } finally {
            IoUtils.close(out);
        }
        return file;
    }

    /**
     * 将流的内容写入文件<br>
     * 此方法不会关闭输入流
     * 
     * @param in 输入流，不关闭
     * @return dest
     * @throws IORuntimeException IO异常
     */
    public File writeFromStream(InputStream in) throws IORuntimeException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(FileUtils.touch(file));
            IoUtils.copy(in, out);
        }catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtils.close(out);
        }
        return file;
    }

    /**
     * 获得一个输出流对象
     * 
     * @return 输出流对象
     * @throws IORuntimeException IO异常
     */
    public BufferedOutputStream getOutputStream() throws IORuntimeException {
        try {
            return new BufferedOutputStream(new FileOutputStream(FileUtils.touch(file)));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获得一个带缓存的写入对象
     * 
     * @param isAppend 是否追加
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public BufferedWriter getWriter(boolean isAppend) throws IORuntimeException {
        try {
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileUtils.touch(file), isAppend), charset));
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获得一个打印写入对象，可以有print
     * 
     * @param isAppend 是否追加
     * @return 打印对象
     * @throws IORuntimeException  IO异常
     */
    public PrintWriter getPrintWriter(boolean isAppend) throws IORuntimeException {
        return new PrintWriter(getWriter(isAppend));
    }
    
    /**
     * 检查文件
     * 
     * @throws IORuntimeException  IO异常
     */
    private void checkFile() throws IORuntimeException {
        Assert.notNull(file, "File to write source is null !");
        if(this.file.exists() && false == file.isFile()){
            throw new IORuntimeException("File [{}] is not a file !", this.file.getAbsoluteFile());
        }
    }
}
