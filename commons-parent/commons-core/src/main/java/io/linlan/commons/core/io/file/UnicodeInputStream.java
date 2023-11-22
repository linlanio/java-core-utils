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
package io.linlan.commons.core.io.file;

import io.linlan.commons.core.CharsetUtils;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.InputStream;

/**
 * This InputStream will recognize unicode BOM marks
 * Filename:UnicodeInputStream.java
 * Desc:
 * This InputStream will recognize unicode BOM marks and will skip bytes if
 * getCharset() method is called before any of the read(...) methods.
 *
 * Usage pattern: String enc = "ISO-8859-1"; // or NULL to use systemdefault
 * FileInputStream fis = new FileInputStream(file); UnicodeInputStream uin = new
 * UnicodeInputStream(fis, enc); enc = uin.getCharset(); // check and skip
 * possible BOM bytes InputStreamReader in; if (enc == null) in = new
 * InputStreamReader(uin); else in = new InputStreamReader(uin, enc);
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-07-12 10:31 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class UnicodeInputStream extends InputStream {
    /**
     * internal input streams
     */
    PushbackInputStream internalIn;
    /**
     * is initialed, the default is false
     */
    boolean isInitialed = false;
    /**
     * the default charset
     */
    String defCharset;
    /**
     * charset
     */
    String charset;

    /**
     * the default BOM size
     */
    private static final int BOM_SIZE = 4;

    /**
     * constructor of self with in, use default charset
     * @param in the input
     */
    public UnicodeInputStream(InputStream in) {
        internalIn = new PushbackInputStream(in, BOM_SIZE);
        this.defCharset = CharsetUtils.defaultCharset().name();
    }

    /**
     * constructor of self with in, charset
     * @param in the input
     * @param defEncoding default encoding utf-8
     */
    public UnicodeInputStream(InputStream in, String defEncoding) {
        internalIn = new PushbackInputStream(in, BOM_SIZE);
        this.defCharset = defEncoding;
    }

    /**
     * get default charset
     * @return defCharset
     */
    public String getDefCharset() {
        return defCharset;
    }

    /**
     * get charset
     * @return charset
     */
    public String getCharset() {
        if (!isInitialed) {
            try {
                init();
            } catch (IOException ex) {
                IllegalStateException ise = new IllegalStateException(
                        "Init method failed.");
                ise.initCause(ise);
                throw ise;
            }
        }
        return charset;
    }

    /**
     * Read-ahead four bytes and check for BOM marks. Extra bytes are unread
     * back to the stream, only BOM bytes are skipped.
     * @throws IOException IO异常
     */
    protected void init() throws IOException {
        if (isInitialed)
            return;

        byte bom[] = new byte[BOM_SIZE];
        int n, unread;
        n = internalIn.read(bom, 0, bom.length);

        if ((bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00)
                && (bom[2] == (byte) 0xFE) && (bom[3] == (byte) 0xFF)) {
            charset = "UTF-32BE";
            unread = n - 4;
        } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)
                && (bom[2] == (byte) 0x00) && (bom[3] == (byte) 0x00)) {
            charset = "UTF-32LE";
            unread = n - 4;
        } else if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB)
                && (bom[2] == (byte) 0xBF)) {
            charset = "UTF-8";
            unread = n - 3;
        } else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
            charset = "UTF-16BE";
            unread = n - 2;
        } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
            charset = "UTF-16LE";
            unread = n - 2;
        } else {
            // Unicode BOM mark not found, unread all bytes
            charset = defCharset;
            unread = n;
        }
        // System.out.println("read=" + n + ", unread=" + unread);

        if (unread > 0)
            internalIn.unread(bom, (n - unread), unread);

        isInitialed = true;
    }

    /**
     * close
     * @throws IOException exception
     */
    public void close() throws IOException
    {
        // init();
        isInitialed = true;
        internalIn.close();
    }

    public int read() throws IOException {
        // init();
        isInitialed = true;
        return internalIn.read();
    }
}