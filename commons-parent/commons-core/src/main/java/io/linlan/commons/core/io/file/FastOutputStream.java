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

import io.linlan.commons.core.CharsetUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * the Fast OutputStream class to define stream operation
 * Filename:FastOutputStream.java
 * Desc:Fast OutputStream to provide stream method
 * it use FastByteBuffer with high performance and auto increase size
 * it provide write to IO operations
 * <p>
 * you can use {@link #toByte()} and {@link #toString()} to get stream
 * <p>
 * the close {@link #close()} will not truely close the stream
 * <p>
 *
 * Createtime 2020/7/12 11:45 PM
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public class FastOutputStream extends OutputStream {

    /**
     * fast byte buffer
     */
    private final FastByteBuffer fastBuffer;

    /**
     * constructor of self to set size with default size
     */
    public FastOutputStream() {
        this(FastByteBuffer.DEFAULT_BUFFER_SIZE);
    }

    /**
     * constructor of self to set size with input size
     */
    public FastOutputStream(int size) {
        fastBuffer = new FastByteBuffer(size);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        fastBuffer.append(b, off, len);
    }

    @Override
    public void write(int b) {
        fastBuffer.append((byte) b);
    }

    /**
     * get the size
     *
     * @return {@link FastByteBuffer#size}
     */
    public int size() {
        return fastBuffer.size();
    }

    /**
     * do nothing
     */
    @Override
    public void close() {
        // nop
    }

    /**
     * reset self to initial {@link FastByteBuffer#reset}
     */
    public void reset() {
        fastBuffer.reset();
    }

    public void writeTo(OutputStream out) throws IOException {
        int index = fastBuffer.index();
        for (int i = 0; i < index; i++) {
            byte[] buf = fastBuffer.array(i);
            out.write(buf);
        }
        out.write(fastBuffer.array(index), 0, fastBuffer.offset());
    }

    /**
     * to get byte stream of whole buffer
     *
     * @return the byte in buffer {@link FastByteBuffer#toByte}
     */
    public byte[] toByte() {
        return fastBuffer.toByte();
    }

    @Override
    public String toString() {
        return new String(toByte());
    }

    /**
     * toString with input Charset get byte stream of whole buffer
     *
     * @param charsetName charset name
     * @return the string of FastOutputStream
     */
    public String toString(String charsetName) throws UnsupportedEncodingException {
        return toString(CharsetUtils.getCharset(charsetName));
    }

    /**
     * toString with input Charset get byte stream of whole buffer
     *
     * @param charset the input charset
     * @return the string of FastOutputStream
     */
    public String toString(Charset charset) {
        return new String(toByte(), charset);
    }

}