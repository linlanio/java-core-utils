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

import java.io.File;
import java.nio.charset.Charset;

/**
 * File Wrapper to deal with write file operations.
 * Filename:FileWrapper.java
 * Desc: the file wrapper
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-08-25 10:39 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class FileWrapper {
    /**
     * the file to wrapper
     */
    protected File file;
    /**
     * the charset of file operations
     */
    protected Charset charset;

    /**
     * constructor of self with input file and charset
     * @param file the input file
     * @param charset charset
     */
    public FileWrapper(File file, Charset charset) {
        this.file = file;
        this.charset = charset;
    }

    /**
     * get the file
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * set the file
     * @param file the input file
     * @return this
     */
    public FileWrapper setFile(File file) {
        this.file = file;
        return this;
    }

    /**
     * get the charset
     * @return the charset
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * set the charset
     * @param charset the input charset
     * @return this
     */
    public FileWrapper setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

}

