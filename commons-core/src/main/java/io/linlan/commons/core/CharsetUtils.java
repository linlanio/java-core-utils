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

import io.linlan.commons.core.abs.CharsetConstants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Charset class to provide utils for use
 * Filename:CharsetUtils.java
 * Desc:Charset utils include convert, default utils
 *
 * CreateTime:2020-06-30 10:15 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class CharsetUtils extends CharsetConstants {

    /**
     * constructor of self
     */
    private CharsetUtils() {

    }


    /**
     * get charset from name
     * @param name charset name, if null the return default charset
     * @return Charset
     */
    public static Charset getCharset(final String name) {
        return StringUtils.isBlank(name) ? Charset.defaultCharset() : Charset.forName(name);
    }

    /**
     * convert source string from srcCharset to destCharset
     * @param source source string
     * @param srcCharsetName source charset name, the default is ISO-8859-1
     * @param destCharsetName desi charset name, the default is UTF-8
     * @return Charset
     */
    public static String convert(final String source, String srcCharsetName, String destCharsetName) {
        return convert(source, getCharset(srcCharsetName), getCharset(destCharsetName));
    }

    /**
     * convert source string from srcCharset to destCharset
     * @param source source string
     * @param srcCharset source Charset, the default is ISO-8859-1
     * @param destCharset desi Charset, the default is UTF-8
     * @return Charset
     */
    public static String convert(final String source, Charset srcCharset, Charset destCharset) {
        if (null == srcCharset)
        {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset)
        {
            destCharset = StandardCharsets.UTF_8;
        }

        if (StringUtils.isBlank(source) || srcCharset.equals(destCharset))
        {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }


    /**
     * get the default charset
     *
     * @return Charset
     */
    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }

}
