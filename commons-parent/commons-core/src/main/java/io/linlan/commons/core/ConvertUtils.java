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

import io.linlan.commons.core.lang.Assert;
import io.linlan.commons.core.lang.BasicType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * the Convert utils is to convert source to dest
 * Filename:ConvertUtils.java
 * Desc:Convert utils is to deal with many type of source
 * to dest type, some result with default sources.
 *
 * CreateTime:2020-07-26 10:44 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class ConvertUtils {

    /**
     * constructor of self
     */
    private ConvertUtils() {
    }

    /**
     * 转换为字符串<br>
     * 如果给定的值为null，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static String toStr(Object source, String defValue) {
        return convert(String.class, source, defValue);
    }

    /**
     * 转换为字符串<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static String toStr(Object source) {
        return toStr(source, null);
    }

    /**
     * 转换为字符<br>
     * 如果给定的值为null，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Character toChar(Object source, Character defValue) {
        return convert(Character.class, source, defValue);
    }

    /**
     * 转换为字符<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Character toChar(Object source) {
        return toChar(source, null);
    }

    /**
     * 转换为byte<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Byte toByte(Object source, Byte defValue) {
        return convert(Byte.class, source, defValue);
    }

    /**
     * 转换为byte<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Byte toByte(Object source) {
        return toByte(source, null);
    }

    /**
     * 转换为Short<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Short toShort(Object source, Short defValue) {
        return convert(Short.class, source, defValue);
    }

    /**
     * 转换为Short<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Short toShort(Object source) {
        return toShort(source, null);
    }

    /**
     * 转换为Number<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Number toNumber(Object source, Number defValue) {
        return convert(Number.class, source, defValue);
    }

    /**
     * 转换为Number<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Number toNumber(Object source) {
        return toNumber(source, null);
    }

    /**
     * 转换为int<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Integer toInt(Object source, Integer defValue) {
        return convert(Integer.class, source, defValue);
    }

    /**
     * 转换为int<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Integer toInt(Object source) {
        return toInt(source, null);
    }

    /**
     * 转换为Integer数组<br>
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Integer[] toIntArray(Object source) {
        return convert(Integer[].class, source);
    }

    /**
     * 转换为long<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Long toLong(Object source, Long defValue) {
        return convert(Long.class, source, defValue);
    }

    /**
     * 转换为long<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Long toLong(Object source) {
        return toLong(source, null);
    }

    /**
     * 转换为Long数组<br>
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Long[] toLongArray(Object source) {
        return convert(Long[].class, source);
    }

    /**
     * 转换为double<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Double toDouble(Object source, Double defValue) {
        return convert(Double.class, source, defValue);
    }

    /**
     * 转换为double<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Double toDouble(Object source) {
        return toDouble(source, null);
    }

    /**
     * 转换为Double数组<br>
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Double[] toDoubleArray(Object source) {
        return convert(Double[].class, source);
    }

    /**
     * 转换为Float<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Float toFloat(Object source, Float defValue) {
        return convert(Float.class, source, defValue);
    }

    /**
     * 转换为Float<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Float toFloat(Object source) {
        return toFloat(source, null);
    }

    /**
     * 转换为Float数组<br>
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Float[] toFloatArray(Object source) {
        return convert(Float[].class, source);
    }

    /**
     * 转换为boolean<br>
     * String支持的值为：true、false、yes、ok、no，1,0 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static Boolean toBool(Object source, Boolean defValue) {
        return convert(Boolean.class, source, defValue);
    }

    /**
     * 转换为boolean<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Boolean toBool(Object source) {
        return toBool(source, null);
    }

    /**
     * 转换为Boolean数组<br>
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static Boolean[] toBooleanArray(Object source) {
        return convert(Boolean[].class, source);
    }

    /**
     * 转换为BigInteger<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object source, BigInteger defValue) {
        return convert(BigInteger.class, source, defValue);
    }

    /**
     * 转换为BigInteger<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object source) {
        return toBigInteger(source, null);
    }

    /**
     * 转换为BigDecimal<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @param defValue 转换错误时的默认值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object source, BigDecimal defValue) {
        return convert(BigDecimal.class, source, defValue);
    }

    /**
     * 转换为BigDecimal<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     * 
     * @param source 被转换的值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object source) {
        return toBigDecimal(source, null);
    }

    /**
     * 转换为Enum对象<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 
     * @param <E> 枚举类型
     * @param clazz Enum的Class
     * @param source 值
     * @param defValue 默认值
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object source, E defValue) {
        if (source == null) {
            return defValue;
        }
        if (clazz.isAssignableFrom(source.getClass())) {
            @SuppressWarnings("unchecked")
            E myE = (E) source;
            return myE;
        }
        final String sourceStr = toStr(source, null);
        if (StringUtils.isBlank(sourceStr)) {
            return defValue;
        }
        try {
            return Enum.valueOf(clazz, sourceStr);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 转换为Enum对象<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 
     * @param <E> 枚举类型
     * @param clazz Enum的Class
     * @param source 值
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object source) {
        return toEnum(clazz, source, null);
    }

    /**
     * 转换值为指定类型
     * 
     * @param <T> 目标类型
     * @param type 类型
     * @param source 值
     * @return 转换后的值
     */
    public static <T> T convert(Class<T> type, Object source) {
        return convert(type, source, null);
    }

    /**
     * 转换值为指定类型
     * 
     * @param <T> 目标类型
     * @param type 类型
     * @param source 值
     * @param defValue 默认值
     * @return 转换后的值
     * @throws CoreException 转换器不存在
     */
    public static <T> T convert(Class<T> type, Object source, T defValue) throws CoreException {
        return convert(type, source, defValue, true);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T> 转换的目标类型（转换器转换到的类型）
     * @param type 类型
     * @param value 值
     * @param defValue 默认值
     * @param isCustomFirst 是否自定义转换器优先
     * @return 转换后的值
     * @throws CoreException 转换器不存在
     */
    private static <T> T convert(Class<T> type, Object value, T defValue, boolean isCustomFirst) throws CoreException{
        if(null == type && null == defValue){
            throw new NullPointerException("[type] and [defValue] are both null, we can not know what type to convert !");
        }
        if(null == value ){
            return defValue;
        }
        if(null == type){
            type = (Class<T>) defValue.getClass();
        }
        //默认强转
        if(type.isInstance(value)){
            return (T) value;
        }

        //数组强转
        final Class<?> valueClass = value.getClass();
        if(type.isArray() && valueClass.isArray()){
            try {
                return (T) ArrayUtils.cast(type, value);
            } catch (Exception e) {
                //强转失败进行下一步
            }
        }


//        Class<?> tarType = value.getClass().getComponentType();
        Class<?> targetType = getTargetType(value.getClass());
        if(null == targetType && null == defValue){
            throw new NullPointerException("[type] and [defValue] are both null for Converter ["+value.getClass()+"], we can not know what type to convert !");
        }
        if(null == targetType){
            targetType = defValue.getClass();
        }

        if(targetType.isPrimitive()){
            //原始类型直接调用内部转换，内部转换永远不会返回null
            return convertInternal(value, (T) value.getClass());
        }

        if(null == value){
            return defValue;
        }
        if(null == defValue || targetType.isInstance(defValue)){
            if(targetType.isInstance(value)){
                //已经是目标类型，不需要转换
                return (T) targetType.cast(value);
            }
            final T convertInternal = convertInternal(value, (T) value.getClass());
            return ((null == convertInternal) ? defValue : convertInternal);
        }else{
            throw new IllegalArgumentException(MessageFormat.format("Default value [{0}] is not the instance of [{1}]]", defValue, targetType));
        }
    }

    private static <T> T convertInternal(Object value, T type) {
        if (null == value) {
            return null;
        }
        if (type instanceof String) {
            return (T) value.toString();
        }
        return (T) value;

    }

    private static Class<?> getTargetType(Class<?> clazz) {
        return ClassUtils.getTypeArgument(clazz);
    }

    /**
     * 半角转全角
     * 
     * @param input String.
     * @return 全角字符串.
     */
    public static String toSBC(String input) {
        return toSBC(input, null);
    }

    /**
     * 半角转全角
     * 
     * @param input String
     * @param notConvertSet 不替换的字符集合
     * @return 全角字符串.
     */
    public static String toSBC(String input, Set<Character> notConvertSet) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (null != notConvertSet && notConvertSet.contains(c[i])) {
                // 跳过不替换的字符
                continue;
            }

            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 全角转半角
     * 
     * @param input String.
     * @return 半角字符串
     */
    public static String toDBC(String input) {
        return toDBC(input, null);
    }

    /**
     * 替换全角为半角
     * 
     * @param text 文本
     * @param notConvertSet 不替换的字符集合
     * @return 替换后的字符
     */
    public static String toDBC(String text, Set<Character> notConvertSet) {
        char c[] = text.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (null != notConvertSet && notConvertSet.contains(c[i])) {
                // 跳过不替换的字符
                continue;
            }

            if (c[i] == '\u3000' || c[i] == '\u00a0' || c[i] == '\u2007' || c[i] == '\u202F') {
                //\u3000是中文全角空格，\u00a0、\u2007、\u202F是不间断空格
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        String returnString = new String(c);

        return returnString;
    }

    /**
     * 字符串转换成十六进制字符串
     * 
     * @param str 待转换的ASCII字符串
     * @param charset 编码
     * @return 16进制字符串
     */
    public static String toHex(String str, Charset charset) {
        return HexUtils.encodeHexStr(str.getBytes(charset));
    }

    /**
     * byte数组转16进制串
     * 
     * @param bytes 被转换的byte数组
     * @return 转换后的值
     */
    public static String toHex(byte[] bytes) {
        return HexUtils.encodeHexStr(bytes);
    }

    /**
     * Hex字符串转换为Byte值
     * 
     * @param src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]
     */
    public static byte[] hexToBytes(String src) {
        return HexUtils.decodeHex(src.toCharArray());
    }

    /**
     * 十六进制转换字符串
     * 
     * @param hexStr Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @param charset 编码 {@link Charset}
     * @return 对应的字符串
     */
    public static String hexStrToStr(String hexStr, Charset charset) {
        return HexUtils.decodeHexStr(hexStr, charset);
    }

    /**
     * String的字符串转换成unicode的String
     * 
     * @param strText 全角字符串
     * @return String 每个unicode之间无分隔符
     */
    public static String strToUnicode(String strText) {
        int strLength = strText.length();
        final StringBuilder str = new StringBuilder(strLength * 6);
        String strHex;
        int strHexLen;
        for (int i = 0; i < strLength; i++) {
            strHex = Integer.toHexString(strText.charAt(i));
            strHexLen = strHex.length();
            str.append("\\u");
            //对不够4位的在前补零
            if(strHexLen > 0 && strHexLen < 4){
                str.append(StringUtils.repeat('0', 4 - strHexLen));
            }
            str.append(strHex);
        }
        return str.toString();
    }

    /**
     * unicode的String转换成String的字符串
     * 
     * @param unicode Unicode符
     * @return String 字符串
     */
    public static String unicodeToStr(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = StringUtils.split(unicode, "\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 给定字符串转换字符编码<br>
     * 如果参数为空，则返回原字符串，不报错。
     * 
     * @param str 被转码的字符串
     * @param sourceCharset 原字符集
     * @param destCharset 目标字符集
     * @return 转换后的字符串
     */
    public static String convertCharset(String str, String sourceCharset, String destCharset) {
        if (StringUtils.hasBlank(str, sourceCharset, destCharset)) {
            return str;
        }

        return CharsetUtils.convert(str, sourceCharset, destCharset);
    }

    /**
     * 转换时间单位
     * 
     * @param sourceDuration 时长
     * @param sourceUnit 源单位
     * @param destUnit 目标单位
     * @return 目标单位的时长
     */
    public static long convertTime(long sourceDuration, TimeUnit sourceUnit, TimeUnit destUnit) {
        Assert.notNull(sourceUnit, "sourceUnit is null !");
        Assert.notNull(destUnit, "destUnit is null !");
        return destUnit.convert(sourceDuration, sourceUnit);
    }

    /**
     * 数字金额大写转换 先写个完整的然后将如零拾替换成零
     * 
     * @param n 数字
     * @return 中文大写数字
     */
    public static String digitUppercase(double n) {
        String fraction[] = { "角", "分" };
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

        String head = n < 0 ? "负" : "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    /**
     * 原始类转为包装类，非原始类返回原类
     *
     * @see BasicType#wrap(Class)
     * @param clazz 原始类
     * @return 包装类
     */
    public static Class<?> wrap(Class<?> clazz) {
        return BasicType.wrap(clazz);
    }

    /**
     * 包装类转为原始类，非包装类返回原类
     *
     * @see BasicType#unWrap(Class)
     * @param clazz 包装类
     * @return 原始类
     */
    public static Class<?> unWrap(Class<?> clazz) {
        return BasicType.unWrap(clazz);
    }
}
