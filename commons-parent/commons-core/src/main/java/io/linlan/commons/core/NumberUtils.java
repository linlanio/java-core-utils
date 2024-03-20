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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Number class to provide utils for use
 * Filename:NumberUtils.java
 * Desc:Number utils include add, sub, mux, div methods
 * the utils is for commons group packages to use
 * when do operation, need to use {@link BigDecimal}
 *
 * Createtime 2020/7/01 9:00 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class NumberUtils {

    /**
     * default div scale
     */
    private static final int DEFAULT_DIV_SCALE = 10;

    /**
     * constructor of self
     */
    private NumberUtils() {

    }

    /**
     * if the source include space, whitespace, tab, nbsp, entire space<br>
     *
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @param source source char
     * @return true, false
     */
    public static boolean isBlankChar(char source) {
        return isBlankChar((int) source);
    }

    /**
     * if the source include space, whitespace, tab, nbsp, entire space<br>
     *
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @param source source char
     * @return true, false
     */
    public static boolean isBlankChar(int source) {
        return Character.isWhitespace(source) || Character.isSpaceChar(source);
    }


    /**
     * add with number to provide accurate result
     *
     * @param n1 augend
     * @param n2 addend
     * @return add result of double
     */
    public static double add(double n1, double n2) {
        BigDecimal b1 = new BigDecimal(Double.toString(n1));
        BigDecimal b2 = new BigDecimal(Double.toString(n2));
        return b1.add(b2).doubleValue();
    }

    /**
     * sub with number to provide accurate result
     *
     * @param n1 minuend
     * @param n2 meiosis
     * @return sub result of double
     */
    public static double sub(double n1, double n2) {
        BigDecimal b1 = new BigDecimal(Double.toString(n1));
        BigDecimal b2 = new BigDecimal(Double.toString(n2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * mul with number to provide accurate result
     *
     * @param n1 faciend
     * @param n2 multiplier
     * @return mul result of double
     */
    public static double mul(double n1, double n2) {
        BigDecimal b1 = new BigDecimal(Double.toString(n1));
        BigDecimal b2 = new BigDecimal(Double.toString(n2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * div with number to provide accurate result
     * when can not be eliminated, Accurate to 10 decimal places, use "four in five" strategy
     * use the default scale of define 10
     *
     * @param n1 dividend
     * @param n2 GCD
     * @return div result of double
     */
    public static double div(double n1, double n2) {
        return div(n1, n2, DEFAULT_DIV_SCALE);
    }

    /**
     * div with number to provide accurate result
     * when can not be eliminated, Accurate to 10 decimal places, use "four in five" strategy
     * the input scale is {@link RoundingMode#HALF_UP}
     *
     * @param n1 dividend
     * @param n2 GCD
     * @param scale scale of accurate, use absolute value
     * @return div result of double
     */
    public static double div(double n1, double n2, int scale) {
        return div(n1, n2, scale, RoundingMode.HALF_UP);
    }

    /**
     * div with number to provide accurate result
     * when can not be eliminated, Accurate to 10 decimal places, use "four in five" strategy
     *
     * @param n1 dividend
     * @param n2 GCD
     * @param scale scale of accurate, use absolute value
     * @param roundingMode Retain decimal mode {@link RoundingMode}
     * @return div result of double
     */
    public static double div(double n1, double n2, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            scale = -scale;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(n1));
        BigDecimal b2 = new BigDecimal(Double.toString(n2));
        return b1.divide(b2, scale, roundingMode).doubleValue();
    }

    /**
     * round with number to retain fixed decimal digits to provide accurate result
     * use "four in five" strategy
     * the default scale is {@link RoundingMode#HALF_UP}
     *
     * @param source source double
     * @param scale scale of accurate, use absolute value
     * @return round result of double
     */
    public static double round(double source, int scale) {
        return round(source, scale, RoundingMode.HALF_UP);
    }

    /**
     * round with number to retain fixed decimal digits to provide accurate result
     * use "four in five" strategy
     * the default scale is {@link RoundingMode#HALF_UP}
     *
     * @param source source double of string
     * @param scale scale of accurate, use absolute value
     * @return round result of double
     */
    public static double round(String source, int scale) {
        return round(source, scale, RoundingMode.HALF_UP);
    }

    /**
     * round with number to retain fixed decimal digits to provide accurate result
     * use "four in five" strategy
     *
     * @param source source double
     * @param scale scale of accurate, use absolute value
     * @param roundingMode  {@link RoundingMode}
     * @return round result of double
     */
    public static double round(double source, int scale, RoundingMode roundingMode) {
        return round(Double.toString(source), scale, roundingMode);
    }

    /**
     * round with number to retain fixed decimal digits to provide accurate result
     * use "four in five" strategy
     *
     * @param source source double of string
     * @param scale scale of accurate, use absolute value
     * @param roundingMode  {@link RoundingMode}
     * @return round result of double
     */
    public static double round(String source, int scale, RoundingMode roundingMode) {
        final BigDecimal b = new BigDecimal(source);
        return b.setScale(scale, roundingMode).doubleValue();
    }

    /**
     * round with number to retain fixed decimal digits to provide accurate result
     * use "four in five" strategy, the result is string
     *
     * @param source source double
     * @param scale scale of accurate, use absolute value
     * @return round result of string
     */
    public static String roundToString(double source, int scale) {
        return String.format("%." + scale + 'f', source);
    }

    /**
     * format the double source with pattern<br>
     *  {@link DecimalFormat} <br>
     *
     * @param pattern use # or 0 to define length, 0 fill number, # the position<br>
     *            <ul>
     *            <li>0:1 Integer</li>
     *            <li>0.00:1 Integer and 2 decimal </li>
     *            <li>00.000:2 Integer and 3 decimal </li>
     *            <li>#:all Integer part</li>
     *            <li>#.##%:use percent 2 decimal</li>
     *            <li>#.#####E0:use E and 5 decimal</li>
     *            <li>,###:every three Integer divide with , such as:299,792,458</li>
     *            </ul>
     * @param source double value
     * @return String of format
     */
    public static String decimalFormat(String pattern, double source) {
        return new DecimalFormat(pattern).format(source);
    }

    /**
     * format the double source with pattern<br>
     *  {@link DecimalFormat} <br>
     *
     * @param pattern use # or 0 to define length, 0 fill number, # the position<br>
     *            <ul>
     *            <li>0:1 Integer</li>
     *            <li>0.00:1 Integer and 2 decimal </li>
     *            <li>00.000:2 Integer and 3 decimal </li>
     *            <li>#:all Integer part</li>
     *            <li>#.##%:use percent 2 decimal</li>
     *            <li>#.#####E0:use E and 5 decimal</li>
     *            <li>,###:every three Integer divide with , such as:299,792,458</li>
     *            </ul>
     * @param source long value
     * @return String of format
     */
    public static String decimalFormat(String pattern, long source) {
        return new DecimalFormat(pattern).format(source);
    }

    /**
     * if the string of number is numeric
     *
     * @param source string of number
     * @return true, false
     */
    public static boolean isNumeric(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        char[] chars = source.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        int start = (chars[0] == '-') ? 1 : 0;
        if (sz > start + 1) {
            if (chars[start] == '0' && chars[start + 1] == 'x') {
                int i = start + 2;
                if (i == sz) {
                    return false; // getString == "0x"
                }
                // checking hex (it can't be anything else)
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--; // don't want to loop to the last char, check it afterwords
                // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l' || chars[i] == 'L') {
                // not allowing L with an exponent
                return foundDigit && !hasExp;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }

    /**
     * if the String of number is Integer
     *
     * @param source {@link String}
     * @return true, false
     */
    public static boolean isInteger(String source) {
        if (StringUtils.isNotBlank(source))
            return source.matches("^\\d+$");
        else
            return false;
    }

    /**
     * if the String of number is Double
     *
     * @param source {@link Double}
     * @return true, false
     */
    public static boolean isDouble(String source) {
        try {
            Double.parseDouble(source);
            if (source.contains(".")) return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * if the int is prime number
     * Prime number of prime number table, also called prime number.
     * Of integers in a natural number greater than 1, except for 1 and this integer itself,
     * that cannot be divisible by other natural numbers.
     *
     * @param n source int
     * @return true, false
     */
    public static boolean isPrimes(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * to generate count of random number between Minimum and Maximum with no repeat
     *
     * @param begin Minimum number
     * @param end Maximum number
     * @param count the count of random
     * @return int[]
     */
    public static int[] random(int begin, int end, int count) {
        if (begin > end) {
            int temp = begin;
            begin = end;
            end = temp;
        }
        // begin < end and count can not larger than end - begin
        if ((end - begin) < count) {
            throw new CoreException("Size is larger than range between begin and end!");
        }
        // seed can not repeat
        int[] seed = new int[end - begin];

        for (int i = begin; i < end; i++) {
            seed[i - begin] = i;
        }
        int[] array = new int[count];
        Random ran = new Random();
        // count to create
        for (int i = 0; i < count; i++) {
            // get the next Integer
            int j = ran.nextInt(seed.length - i);
            // get the seed
            array[i] = seed[j];
            // put the last Integer
            seed[j] = seed[seed.length - 1 - i];
        }
        return array;
    }

    /**
     * to generate count of random number between Minimum and Maximum with no repeat
     *
     * @param begin Minimum number
     * @param end Maximum number
     * @param count the count of random
     * @return Integer[]
     */
    public static Integer[] randomBySet(int begin, int end, int count) {
        if (begin > end) {
            int temp = begin;
            begin = end;
            end = temp;
        }
        // 加入逻辑判断，确保begin<end并且size不能大于该表示范围
        if ((end - begin) < count) {
            throw new CoreException("Size is larger than range between begin and end!");
        }

        Random ran = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < count) {
            set.add(begin + ran.nextInt(end - begin));
        }

        Integer[] array = set.toArray(new Integer[count]);
        return array;
    }

    /**
     * the range of int from start and stop, the default step is 1
     *
     * @param begin begin int
     * @param end end int
     * @return int[]
     */
    public static int[] range(int begin, int end) {
        return range(begin, end, 1);
    }

    /**
     * the range of int from start and stop
     *
     * @param begin begin int
     * @param end end int
     * @param step step input
     * @return int[]
     */
    public static int[] range(int begin, int end, int step) {
        if (begin < end) {
            step = Math.abs(step);
        } else if (begin > end) {
            step = -Math.abs(step);
        } else {// start == end
            return new int[] { begin };
        }

        int size = Math.abs((end - begin) / step) + 1;
        int[] values = new int[size];
        int index = 0;
        for (int i = begin; (step > 0) ? i <= end : i >= end; i += step) {
            values[index] = i;
            index++;
        }
        return values;
    }


    /**
     * factorial to get n factorial number multiply result
     *
     * @param n the factory of begin
     * @return the int result
     */
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    /**
     * squareRoot to get root<br>
     * {@link Math#sqrt(double)}
     *
     * @param n source long
     * @return square root
     */
    public static long sqrt(long n) {
        long y = 0;
        long b = (~Long.MAX_VALUE) >>> 1;
        while (b > 0) {
            if (n >= y + b) {
                n -= y + b;
                y >>= 1;
                y += b;
            } else {
                y >>= 1;
            }
            b >>= 2;
        }
        return y;
    }

    /**
     * Greatest common divisor
     * https://en.wikipedia.org/wiki/Greatest_common_divisor
     *
     * @param n1 number
     * @param n2 number
     * @return Greatest common divisor
     */
    public static int GCD(int n1, int n2) {
        while (n1 % n2 != 0) {
            int temp = n1 % n2;
            n1 = n2;
            n2 = temp;
        }
        return n2;
    }

    /**
     * Least common multiple
     * https://en.wikipedia.org/wiki/Least_common_multiple
     *
     * @param n1 number
     * @param n2 number
     * @return Least common multiple
     */
    public static int LCM(int n1, int n2) {
        return n1 * n2 / GCD(n1, n2);
    }


    /**
     * compare with two number
     *
     * @see Character#compare(char, char)
     *
     * @param n1 the first {@code char} to compare
     * @param n2 the second {@code char} to compare
     * @return if (x==y) 0, x&lt;y -1，x&gt;y 1
     */
    public static int compare(char n1, char n2) {
        return n1 - n2;
    }

    /**
     * compare with two number
     *
     * @see Double#compare(double, double)
     *
     * @param n1 the first {@code char} to compare
     * @param n2 the second {@code char} to compare
     * @return if (x==y) 0, x&lt;y -1，x&gt;y 1
     */
    public static int compare(double n1, double n2) {
        return Double.compare(n1, n2);
    }

    /**
     * compare with two number
     *
     * @see Integer#compare(int, int)
     *
     * @param n1 the first {@code char} to compare
     * @param n2 the second {@code char} to compare
     * @return if (x==y) 0, x&lt;y -1，x&gt;y 1
     */
    public static int compare(int n1, int n2) {
        if (n1 == n2) {
            return 0;
        }
        if (n1 < n2) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * compare with two number
     *
     * @see Long#compare(long, long)
     *
     * @param n1 the first {@code char} to compare
     * @param n2 the second {@code char} to compare
     * @return if (x==y) 0, x&lt;y -1，x&gt;y 1
     */
    public static int compare(long n1, long n2) {
        if (n1 == n2) {
            return 0;
        }
        if (n1 < n2) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * compare with two number
     *
     * @see Short#compare(short, short)
     *
     * @param n1 the first {@code char} to compare
     * @param n2 the second {@code char} to compare
     * @return if (x==y) 0, x&lt;y -1，x&gt;y 1
     */
    public static int compare(short n1, short n2) {
        if (n1 == n2) {
            return 0;
        }
        if (n1 < n2) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * compare with two number
     *
     * @see Byte#compare(byte, byte)
     *
     * @param x the first {@code char} to compare
     * @param y the second {@code char} to compare
     * @return if (x==y) 0, x&lt;y -1，x&gt;y 1
     */
    public static int compare(byte x, byte y) {
        return x - y;
    }



    /**
     * divide from the total with part
     *
     * @param total total source
     * @param part every part
     * @return count
     */
    public static int count(int total, int part){
        return (total % part == 0) ? (total / part) : (total / part+1);
    }

    /**
     * number to string<br>
     * @see {@link Number#toString()} without the extra 0 after the decimal point
     *
     * @param source A Number
     * @return A String.
     */
    public static String toString(Number source) {
        if (source == null) {
            throw new NullPointerException("Number is null !");
        }

        if (false == isNumber(source)) {
            throw new IllegalArgumentException("Number is non-finite!");
        }

        // delete the extra 0 after the decimal point
        String string = source.toString();
        if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string.indexOf('E') < 0) {
            while (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith(".")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }


    /**
     * check the source object is number<br>
     * check with Double, Float<br>
     * if is not number or null true, else false
     *
     * @param source source object
     * @return if is not number or null true, else false
     */
    public static boolean isNumber(Object source) {
        if (source != null && source instanceof Number) {
            if (source instanceof Double) {
                if (((Double) source).isInfinite() || ((Double) source).isNaN()) {
                    return false;
                }
            } else if (source instanceof Float) {
                if (((Float) source).isInfinite() || ((Float) source).isNaN()) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * the bubble to create combination use in Chromosphere, Lotto<br>
     * such as:select 5 over 35, you can use bubble(7,5)
     * the same as C75=7*6/2*1
     *
     * @param select select count from total
     * @param min the minimal count of select
     * @return the bubble combination
     */
    public static int bubble(int select, int min) {
        int result;
        result = subFactorial(select, min) / factorial(select - min);
        return result;
    }

    /**
     * get the sub factorial by select and min<br>
     *
     * @param select select count from total
     * @param min the minimal count of select
     * @return the combination
     */
    private static int subFactorial(int select, int min) {
        if (select == min) {
            return 1;
        } else {
            return select * subFactorial(select - 1, min);
        }
    }


}
