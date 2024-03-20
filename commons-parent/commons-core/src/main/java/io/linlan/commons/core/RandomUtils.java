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

import io.linlan.commons.core.abs.CoreExtConstants;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Random class to provide utils for use
 * Filename:RandomUtils.java
 * Desc:Random utils include UUID, random of string, number,
 * mix with string and number methods
 * the utils is for commons group packages to use
 *
 * @author Linlan
 * Createtime 2020/7/11 17:14
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class RandomUtils extends CoreExtConstants {
    private  static SnowFlake idWorker;
    /**
     * RANDOM_STRING_LENGTH
     */
    public static final int RANDOM_STRING_LENGTH = 15;
    /**
     * RANDOM_LONG_LENGTH
     */
    public static final int RANDOM_LONG_LENGTH = 8;

    private static Long lastMills = 0L;
    /**
     * constructor of self
     */
    private RandomUtils() {

    }

    /**
     * RANDOM
     */
    private static final Random RANDOM = new Random();

    /**
     * get the whole UUID with -
     * @return {@link String}
     */
    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * get the UUID without - , the length is 32
     * @return {@link String}
     */
    public static String UUID32() {
        return StringUtils.remove(UUID.randomUUID().toString(), StringUtils.C_MINUS);
    }

    /**
     * new a int random
     *
     * @return int in random
     */
    public static int random() {
        Random random = new Random();
        return random.nextInt();
    }

    /**
     * get the random int in bound, the range is [0, bound]
     *
     * @param bound the bound of random int
     * @return int
     */
    public static int random(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    /**
     * get the random int in bound, the range is [min, max]
     *
     * @param min the min of random int
     * @param max the max of random int
     * @return int
     */
    public static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * get the random string with input prefix
     *
     * @param prefix the prefix of random string
     * @return String such as ID_1D3i8Utq
     */
    public static String random(String prefix) {
        return random(prefix, RANDOM_STRING_LENGTH, CHARS_WHOLE_62);
    }

    /**
     * get the random string with input prefix
     *
     * @param prefix the prefix of random string
     * @param count  the length of random string to create
     * @param chars  the character array containing the set of characters to use,
     *  may be null
     * @return the random string
     * @return String such as ID_1D3i8Utq
     */
    public static String random(String prefix, int count, char... chars) {
        if (chars != null && chars.length > 0) {
            return prefix + random(count,  chars);
        }
        return prefix + random(count, CHARS_WHOLE_62);
    }

    /**
     * <p>Creates a random string whose length is the number of characters
     * specified.</p>
     *
     * <p>Characters will be chosen from the set of characters specified.</p>
     *
     * @param count  the length of random string to create
     * @param chars  the character array containing the set of characters to use,
     *  may be null
     * @return the random string
     * @throws IllegalArgumentException if {@code count} &lt; 0.
     */
    public static String random(int count, char... chars) {
        if (chars != null && chars.length > 0) {
            return random(count, 0, chars.length, false, false, chars, RANDOM);
        }
        return random(count, 0, 0, false, false, CHARS_WHOLE_62, RANDOM);
    }

    /**
     * <p>Creates a random string based on a variety of options, using
     * supplied source of randomness.</p>
     *
     * <p>If start and end are both {@code 0}, start and end are set
     * to {@code ' '} and {@code 'z'}, the ASCII printable
     * characters, will be used, unless letters and numbers are both
     * {@code false}, in which case, start and end are set to
     * {@code 0} and {@code Integer.MAX_VALUE}.
     *
     * <p>If set is not {@code null}, characters between start and
     * end are chosen.</p>
     *
     * <p>This method accepts a user-supplied {@link Random}
     * instance to use as a source of randomness. By seeding a single
     * {@link Random} instance with a fixed seed and using it for each call,
     * the same random sequence of strings can be generated repeatedly
     * and predictably.</p>
     *
     * @param count  the length of random string to create
     * @param start  the position in set of chars to start at
     * @param end  the position in set of chars to end before
     * @param letters  only allow letters?
     * @param numbers  only allow numbers?
     * @param chars  the set of chars to choose randoms from, must not be empty.
     *  If {@code null}, then it will use the set of all chars.
     * @param random  a source of randomness.
     * @return the random string
     * @throws ArrayIndexOutOfBoundsException if there are not
     *  {@code (end - start) + 1} characters in the set array.
     * @throws IllegalArgumentException if {@code count} &lt; 0 or the provided chars array is empty.
     */
    public static String random(int count, int start, int end, final boolean letters, final boolean numbers,
                                final char[] chars, final Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        if (chars != null && chars.length == 0) {
            throw new IllegalArgumentException("The chars array must not be empty");
        }

        if (start == 0 && end == 0) {
            if (chars != null) {
                end = chars.length;
            } else {
                if (!letters && !numbers) {
                    end = Integer.MAX_VALUE;
                } else {
                    end = 'z' + 1;
                    start = ' ';
                }
            }
        } else {
            if (end <= start) {
                throw new IllegalArgumentException("Parameter end (" + end + ") must be greater than start (" + start + ")");
            }
        }

        final char[] buffer = new char[count];
        final int gap = end - start;

        while (count-- != 0) {
            char ch;
            if (chars == null) {
                ch = (char) (random.nextInt(gap) + start);
            } else {
                ch = chars[random.nextInt(gap) + start];
            }
            if (letters && Character.isLetter(ch)
                    || numbers && Character.isDigit(ch)
                    || !letters && !numbers) {
                if(ch >= 56320 && ch <= 57343) {
                    if(count == 0) {
                        count++;
                    } else {
                        // low surrogate, insert high surrogate after putting it in
                        buffer[count] = ch;
                        count--;
                        buffer[count] = (char) (55296 + random.nextInt(128));
                    }
                } else if(ch >= 55296 && ch <= 56191) {
                    if(count == 0) {
                        count++;
                    } else {
                        // high surrogate, insert low surrogate before putting it in
                        buffer[count] = (char) (56320 + random.nextInt(128));
                        count--;
                        buffer[count] = ch;
                    }
                } else if(ch >= 56192 && ch <= 56319) {
                    // private high surrogate, no effing clue, so skip it
                    count++;
                } else {
                    buffer[count] = ch;
                }
            } else {
                count++;
            }
        }
        return new String(buffer);
    }

    /**
     * long source to trans StringBuilder in chars range
     *
     * @param source     long
     * @param chars chars to use 62 or 36
     * @return {@link StringBuilder}
     */
    private static StringBuilder longTo(long source, char[] chars) {
        int upgrade = chars.length;
        StringBuilder result = new StringBuilder();
        int last;
        while (source > 0) {
            last = (int) (source % upgrade);
            result.append(chars[last]);
            source /= upgrade;
        }
        return result;
    }


    /**
     * long source to trans StringBuilder in CHARS_WHOLE_62 range
     *
     * @param source     long
     * @return {@link StringBuilder}
     */
    public static String longTo62(long source) {
        return longTo(source, CHARS_WHOLE_62).reverse().toString();
    }


    /**
     * long source to trans StringBuilder in CHARS_WHOLE_62 range
     *
     * @param source
     * @param length, if less then add 0
     * @return {@link String}
     */
    public static String longTo62(long source, int length) {
        StringBuilder sb = longTo(source, CHARS_WHOLE_62);
        for (int i = sb.length(); i < length; i++) {
            sb.append('0');
        }
        return sb.reverse().toString();
    }

    /**
     * long source to trans StringBuilder in CHARS_WHOLE_36 range
     *
     * @param source     long
     * @return {@link StringBuilder}
     */
    public static String longTo36(long source) {
        return longTo(source, CHARS_LOWER_36).reverse().toString();
    }

    /**
     * long source to trans StringBuilder in CHARS_WHOLE_36 range
     *
     * @param source
     * @param length, if less then add 0
     * @return {@link String}
     */
    public static String longTo36(long source, int length) {
        StringBuilder sb = longTo(source, CHARS_LOWER_36);
        for (int i = sb.length(); i < length; i++) {
            sb.append('0');
        }
        return sb.reverse().toString();
    }

    /** to get long from chars 62
     *
     * @param source  chars 62
     * @return      long
     */
    public static long n62ToLong(String source) {
        return toLong(source, CHARS_WHOLE_62);
    }

    /** to get long from chars 36
     *
     * @param source  chars 36
     * @return      long
     */
    public static long n36Tolong(String source) {
        return toLong(source, CHARS_LOWER_36);
    }

    /** to get long from chars
     * @param s string
     * @param chars chars 36 or 62
     * @return  long
     */
    private static long toLong(String s, char[] chars) {
        char[] nc = s.toCharArray();
        long result = 0;
        long pow = 1;
        for (int i = nc.length - 1; i >= 0; i--, pow *= chars.length) {
            int n = findIndex(nc[i], chars);
            result += n * pow;
        }
        return result;
    }

    /** find index of chars by char
     * @param c string
     * @param chars chars 36 or 62
     * @return  int
     */
    private static int findIndex(char c, char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                return i;
            }
        }
        throw new CoreException("Illegal character:" + c);
    }


    /** generate code string with default length RANDOM_STRING_LENGTH
     * @return the random string
     */
    public static String randomCode() {
        return randomCode(RANDOM_STRING_LENGTH);
    }

    /** generate code string with length
     * @param count  the length of random string to create
     * @return the random string
     */
    public static String randomCode(int count) {
        return random("", count);
    }

    /** generate id string with default length RANDOM_STRING_LENGTH
     * @return the random string
     */
    public static String randomSid() {
        return randomSid(RANDOM_STRING_LENGTH);
    }

    /** generate id string with length
     * @param count  the length of random string to create
     * @return the random string
     */
    public static String randomSid(int count) {
        return random("id_", count);
    }

    /** 高并发模式下适用此方法，进行数字处理，采用15位数字进行返回测试
     * @return
     */
    public static long randomLid() {
        if(idWorker==null){
            int workId = (int)(Math.random()*8);//机器码，集群的时候随机生成一个，还是可能会重复。。
            int datacenterId = (int)(Math.random()*8);
            idWorker = new SnowFlake(Long.valueOf(workId), Long.valueOf(datacenterId));
        }
        return  idWorker.nextId();
    }


    /** 高并发模式下适用此方法，进行数字处理，采用15位数字进行返回
     * @param mode      1:  日期格式细化到秒，然后毫秒位采用随机数生成
     *                              2:  日期格式年月日，然后后续采用9位随机数生成
     * @return
     */
    public static long randomLid(int mode) {
        if (mode == 1){
            //年月日时分秒
            String result = "" ;
            String sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            result = sdf;
            return Long.valueOf(result)  * 10 + random(1, 10);
        }else if (mode == 2){
            //年月日+随机数
            String result = "" ;
            result = new SimpleDateFormat("yyyyMMdd").format(new Date());
            return Long.valueOf(result) * 10000000 + random(1, 10000000);
        }else if (mode == 3){
            //随机字符串转为long，调用之前的方法
            return  Math.abs(n62ToLong(randomCode(RANDOM_LONG_LENGTH)));
        }
        return System.currentTimeMillis() * 100;
    }


    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
//        System.out.println(randomLid());
//        System.out.println(randomLid(1));
//        System.out.println(randomLid(2));
//        System.out.println(randomLid(3));
//        System.out.println(randomLid(4));
//        System.out.println(randomLid(2, 5));
//        System.out.println(randomLid(2, 6));
//        System.out.println(randomLid(2, 7));
//        System.out.println(randomLid(2, 8));
//        System.out.println(randomLid(2, 9));
//        System.out.println(randomLid(2, 10));
//        System.out.println(randomLid(2, 20));
//        System.out.println(randomLid(3, 3));
//        System.out.println(randomLid(3, 4));
//        System.out.println(randomLid(3, 6));
//        System.out.println(randomLid(3, 7));
//        System.out.println(randomLid(3, 8));
//        System.out.println(randomLid(3, 9));
//        System.out.println(randomLid(3, 20));
//        System.out.println(randomLid(3, 60));

//                Long bb = randomLid();
//        System.out.println(bb);
//        System.out.println(bb.toString().length());
//        System.out.println(new Date().toLocaleString());
//        System.out.println(new Date().toLocaleString());

//        System.out.println(System.currentTimeMillis());
//        System.out.println(randomLid());
//        System.out.println(randomLid(1));
//        System.out.println(randomLid(2));
//        System.out.println(randomLid(3));
//        System.out.println(System.currentTimeMillis());
//        System.out.println(randomLid(4));
//
//        System.out.println(RandomUtils.randomCode(8));
//        System.out.println(RandomUtils.random("wx_"));
//        System.out.println(RandomUtils.random("gh_", 14));
//        System.out.println(RandomUtils.random("gh_", 14));
//        SnowFlake idWorker = new SnowFlake(7, 7);
//        long aa = idWorker.nextId();
//        System.out.println(aa);
//        System.out.println((aa+"").length());


        for(int i=0;i<100;i++){
            System.out.println(randomLid());
        }
    }

}
