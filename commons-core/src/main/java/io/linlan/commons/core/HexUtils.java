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

import io.linlan.commons.core.abs.HexConstants;

import java.nio.charset.Charset;

/**
 * Hex class to provide utils for use
 * Filename:HexUtils.java
 * Desc:Hex utils include encode, decode with hex and string
 * number with or in hex use hex mode and hex value
 *
 * CreateTime:2020-07-10 8:48 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class HexUtils extends HexConstants {
    /**
     * constructor of self
     */
    private HexUtils() {

    }

    /**
     * is the string is hex number
     * if true use {@code Integer.decode} to int hex number
     * @param source source string
     * @return true, false
     */
    public static boolean isHexNumber(final String source) {
        int index = (source.startsWith("-") ? 1 : 0);
        return (source.startsWith("0x", index) || source.startsWith("0X", index) || source.startsWith("#", index));
    }

    /**
     * encode byte to hex char
     * @param source source byte
     * @return char of hex
     */
    public static char[] encodeHex(final byte[] source) {
        return encodeHex(source, true);
    }

    /**
     * encode source string with input charset to hex char
     * @param source source string
     * @param charset source charset
     * @return char of hex
     */
    public static char[] encodeHex(final String source, Charset charset) {
        return encodeHex(StringUtils.getBytes(source, charset), true);
    }

    /**
     * encode source byte with lowercase char to hex char
     * @param source source data
     * @param toLowerCase true to use lowercase, false to use uppercase
     * @return char of hex
     */
    public static char[] encodeHex(final byte[] source, boolean toLowerCase) {
        return encodeHex(source, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * encode source byte with lowercase char to hex char
     * @param source source data
     * @return char of hex
     */
    public static String encodeHexStr(final byte[] source) {
        return encodeHexStr(source, true);
    }

    /**
     * encode source string with lowercase to hex string
     *
     * @param source source string
     * @param charset source input charset
     * @return string of hex
     */
    public static String encodeHexStr(final String source, Charset charset) {
        return encodeHexStr(StringUtils.getBytes(source, charset), true);
    }

    /**
     * encode source string with lowercase to hex string, use UTF-8 input charset
     *
     * @param source source string
     * @return string of hex
     */
    public static String encodeHexStr(final String source) {
        return encodeHexStr(source, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * encode source string with lowercase or uppercase to hex string
     *
     * @param source source string
     * @param toLowerCase true to use lowercase, false to use uppercase
     * @return string of hex
     */
    public static String encodeHexStr(final byte[] source, boolean toLowerCase) {
        return encodeHexStr(source, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }


    /**
     * decode hex string to string use UTF-8
     * @param source source hex string
     * @return string
     */
    public static String decodeHexStr(final String source) {
        return decodeHexStr(source, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * decode hex string to string use input charset
     * @param source source hex string
     * @param charset source input charset
     * @return string
     */
    public static String decodeHexStr(final String source, Charset charset) {
        if(StringUtils.isEmpty(source)){
            return source;
        }
        return decodeHexStr(source.toCharArray(), charset);
    }

    /**
     * decode hex char to string use input charset
     * @param source source hex string
     * @param charset source input charset
     * @return string
     */
    public static String decodeHexStr(final char[] source, Charset charset){
        return StringUtils.getString(decodeHex(source), charset);
    }

    /**
     * decode hex char to byte
     * @param source source hex string
     * @return byte
     */
    public static byte[] decodeHex(final char[] source) {

        int len = source.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(source[j], j) << 4;
            j++;
            f = f | toDigit(source[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * decode hex char to byte
     * @param source source hex string
     * @return byte
     */
    public static byte[] decodeHex(final String source) {
        if(StringUtils.isEmpty(source)){
            return null;
        }
        return decodeHex(source.toCharArray());
    }

    /**
     * encode source byte with char control
     *
     * @param source source byte
     * @param toDigits to control output char
     * @return string of hex
     */
    private static String encodeHexStr(final byte[] source, char[] toDigits) {
        return new String(encodeHex(source, toDigits));
    }

    /**
     * encode source byte with char control
     *
     * @param source source byte
     * @param toDigits to control output char
     * @return char of hex
     */
    private static char[] encodeHex(final byte[] source, char[] toDigits) {
        int l = source.length;
        char[] out = new char[l << 1];
        // two characters from the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & source[i]) >>> 4];
            out[j++] = toDigits[0x0F & source[i]];
        }
        return out;
    }

    /**
     * Converts a hexadecimal character to an integer.
     *
     * @param ch    A character to convert to an integer digit
     * @param index The index of the character in the source
     * @return An integer
     */
    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }


}