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

import io.linlan.commons.core.abs.CharsetConstants;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * String class to provide utils for use
 * Filename:Md5Utils.java
 * Desc:String utils include isBlank, startsWith, trim methods
 * the utils is for commons group packages to use
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * Createtime 2017/6/30 8:41 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class Md5Utils {
    /**
     * constructor of self
     */
    private Md5Utils() {
    }


    /** 返回小写字母为序列的md5码，采用HexUtils内的方法进行返回
     * @param source the source string
     * @return string
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String md5Lower(String source) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(source.getBytes(CharsetConstants.CHARSET_UTF_8));
        byte[] md5Bytes = digest.digest();
        return new String(HexUtils.encodeHex(md5Bytes));
    }

    /** 返回小写字母为序列的md5码，采用自定义方法进行返回
     * @param source the source string
     * @return string
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String md5LowerSelf(String source) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(source.getBytes(CharsetConstants.CHARSET_UTF_8));
        byte[] md5Bytes = digest.digest();
        return byteArrayToHex(md5Bytes, HexUtils.DIGITS_LOWER);
    }

    /** 将字符流进行运算后返回字符
     * @param source the input source byte
     * @param hexDigits the input hex digits
     * @return string
     */
    public static String byteArrayToHex(byte[] source, char[] hexDigits){
        char[] resultCharArray = new char[source.length * 2];
        int index = 0;
        for (byte b : source) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    /** 返回小写字母为序列的md5码，采用自定义方法进行返回
     * @param source the source string
     * @return string
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String md5UpperSelf(String source) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(source.getBytes(CharsetConstants.CHARSET_UTF_8));
        byte[] md5Bytes = digest.digest();
        return byteArrayToHex(md5Bytes, HexUtils.DIGITS_UPPER);
    }

}

