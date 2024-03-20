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

/**
 * Hash class to provide utils for use
 * Filename:HashUtils.java
 * Desc:Hash utils to create hash code in many algorithm such as:
 * DES, Zobrist, rsHash, pjwHash etc.
 * the FNV1 is recommend.
 *
 * Createtime 2020/7/12 9:22 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class HashUtils {

    /**
     * HASH MASK value, prime
     */
    //1000037 prime
    static int HASH_MASK = 1000037;
    /**
     * constructor of self
     */
    private HashUtils(){
        
    }

    /**
     * additive hash algorithm to create a hash int result
     *
     * @param source source input string
     * @param prime a prime number
     * @return hash int result
     */
    public static int additiveHash(String source, int prime) {
        int hash, i;
        for (hash = source.length(), i = 0; i < source.length(); i++){
            hash += source.charAt(i);
        }
        return hash % prime;
    }

    /**
     * rotating hash algorithm to create a hash int result
     *
     * @param source source input string
     * @param mask a prime number
     * @return hash int result
     */
    public static int rotatingHash(String source, int mask) {
        int hash, i;
        for (hash = source.length(), i = 0; i < source.length(); ++i){
            hash = (hash << 4) ^ (hash >> 28) ^ source.charAt(i);
        }
        return (hash ^ (hash>>10) ^ (hash>>20)) & mask;
//        return hash % mask;
    }


    /**
     * one by one hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int oneByOneHash(String source) {
        int hash, i;
        for (hash = 0, i = 0; i < source.length(); ++i) {
            hash += source.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
         return hash & HASH_MASK;
    }

    /**
     * Bernstein hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int bernstein(String source) {
        int hash = 0;
        int i;
        for (i = 0; i < source.length(); ++i){
            hash = 33 * hash + source.charAt(i);
        }
        return hash;
    }

    /**
     * Universal hash algorithm to create a hash int result
     *
     * @param source source input char
     * @param mask the input mask to and
     * @param tab the input tab
     * @return hash int result
     */
    public static int universal(char[] source, int mask, int[] tab) {
        int hash = source.length, i, len = source.length;
        for (i = 0; i < (len << 3); i += 8) {
            char k = source[i >> 3];
            if ((k & 0x01) == 0){
                hash ^= tab[i + 0];
            }
            if ((k & 0x02) == 0){
                hash ^= tab[i + 1];
            }
            if ((k & 0x04) == 0){
                hash ^= tab[i + 2];
            }
            if ((k & 0x08) == 0){
                hash ^= tab[i + 3];
            }
            if ((k & 0x10) == 0){
                hash ^= tab[i + 4];
            }
            if ((k & 0x20) == 0){
                hash ^= tab[i + 5];
            }
            if ((k & 0x40) == 0){
                hash ^= tab[i + 6];
            }
            if ((k & 0x80) == 0){
                hash ^= tab[i + 7];
            }
        }
        return (hash & mask);
    }

    /**
     * Zobrist hash algorithm to create a hash int result
     *
     * @param source source input char
     * @param mask the input mask to and
     * @param tab the input tab
     * @return hash int result
     */
    public static int zobrist(char[] source, int mask, int[][] tab) {
        int hash, i;
        for (hash = source.length, i = 0; i < source.length; ++i){
            hash ^= tab[i][source[i]];
        }
        return (hash & mask);
    }



    // 32位FNV算法
    static int M_SHIFT = 0;

    /**
     * FNV hash algorithm to create a hash int result
     *
     * @param source source input byte
     * @return hash int result
     */
    public static int fnvHash(byte[] source) {
        int hash = (int) 2166136261L;
        for (byte b : source)
            hash = (hash * 16777619) ^ b;
        if (M_SHIFT == 0)
            return hash;
        return (hash ^ (hash >> M_SHIFT)) & HASH_MASK;
    }

    /**
     * optimization FNV1 hash algorithm to create a hash int result
     *
     * @param source source input byte
     * @return hash int result
     */
    public static int fnvHash1(byte[] source) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (byte b : source)
            hash = (hash ^ b) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    /**
     * optimization FNV1 hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int fnvHash1(String source) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < source.length(); i++)
            hash = (hash ^ source.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    /**
     * Thomas Wang hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int intHash(int source) {
        source += ~(source << 15);
        source ^= (source >>> 10);
        source += (source << 3);
        source ^= (source >>> 6);
        source += ~(source << 11);
        source ^= (source >>> 16);
        return source;
    }

    /**
     * RS hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int rsHash(String source) {
        int b = 378551;
        int a = 63689;
        int hash = 0;

        for (int i = 0; i < source.length(); i++) {
            hash = hash * a + source.charAt(i);
            a = a * b;
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * JS hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int jsHash(String source) {
        int hash = 1315423911;

        for (int i = 0; i < source.length(); i++) {
            hash ^= ((hash << 5) + source.charAt(i) + (hash >> 2));
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * PJW hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int pjwHash(String source) {
        int bitsInUnsignedInt = 32;
        int threeQuarters = (bitsInUnsignedInt * 3) / 4;
        int oneEighth = bitsInUnsignedInt / 8;
        int highBits = 0xFFFFFFFF << (bitsInUnsignedInt - oneEighth);
        int hash = 0;
        int temp = 0;

        for (int i = 0; i < source.length(); i++) {
            hash = (hash << oneEighth) + source.charAt(i);

            if ((temp = hash & highBits) != 0) {
                hash = ((hash ^ (temp >> threeQuarters)) & (~highBits));
            }
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * ELF hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int elfHash(String source) {
        int hash = 0;
        int x = 0;

        for (int i = 0; i < source.length(); i++) {
            hash = (hash << 4) + source.charAt(i);
            if ((x = (int) (hash & 0xF0000000L)) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * BKDR hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int bkdrHash(String source) {
        int seed = 13131; // 31 131 1313 13131 131313
        int hash = 0;

        for (int i = 0; i < source.length(); i++) {
            hash = (hash * seed) + source.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * SDBM hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int sdbmHash(String source) {
        int hash = 0;

        for (int i = 0; i < source.length(); i++) {
            hash = source.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * DJB hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int djbHash(String source) {
        int hash = 5381;

        for (int i = 0; i < source.length(); i++) {
            hash = ((hash << 5) + hash) + source.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * DEK hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int dekHash(String source) {
        int hash = source.length();

        for (int i = 0; i < source.length(); i++) {
            hash = ((hash << 5) ^ (hash >> 27)) ^ source.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * AP hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int apHash(String source) {
        int hash = 0;

        for (int i = 0; i < source.length(); i++) {
            hash ^= ((i & 1) == 0) ? ((hash << 7) ^ source.charAt(i) ^ (hash >> 3))
                    : (~((hash << 11) ^ source.charAt(i) ^ (hash >> 5)));
        }

        return (hash & 0x7FFFFFFF);
//        return hash;
    }

    /**
     * TianL hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static long tianlHash(String source) {
        long hash = 0;

        int iLength = source.length();
        if (iLength == 0){
            return 0;
        }

        if (iLength <= 256){
            hash = 16777216L * (iLength - 1);
        }else{
            hash = 4278190080L;
        }

        int i;

        char ucChar;

        if (iLength <= 96) {
            for (i = 1; i <= iLength; i++) {
                ucChar = source.charAt(i - 1);
                if (ucChar <= 'Z' && ucChar >= 'A') {
                    ucChar = (char) (ucChar + 32);
                }
                hash += (3 * i * ucChar * ucChar + 5 * i * ucChar + 7 * i + 11 * ucChar) % 16777216;
            }
        } else {
            for (i = 1; i <= 96; i++) {
                ucChar = source.charAt(i + iLength - 96 - 1);
                if (ucChar <= 'Z' && ucChar >= 'A'){
                    ucChar = (char) (ucChar + 32);
                }
                hash += (3 * i * ucChar * ucChar + 5 * i * ucChar + 7 * i + 11 * ucChar) % 16777216;
            }
        }
        if (hash < 0) {
            hash *= -1;
        }
        return hash;
    }

    /**
     * Java default hash algorithm to create a hash int result
     *
     * @param source source input string
     * @return hash int result
     */
    public static int javaHash(String source) {
        int h = 0;
        int off = 0;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            h = 31 * h + source.charAt(off++);
        }
        return h;
    }

    /**
     * mix hash algorithm to create a hash long result
     *
     * @param source source input string
     * @return hash int result
     */
    public static long mixHash(String source) {
        long hash = source.hashCode();
        hash <<= 32;
        hash |= fnvHash1(source);
        return hash;
    }
}