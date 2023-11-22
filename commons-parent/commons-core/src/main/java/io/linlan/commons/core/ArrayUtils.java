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

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Array class to provide utils for use
 * Filename:ArrayUtils.java
 * Desc:Array utils include empty, null, resize, add, new methods
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/6/28 8:47 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class ArrayUtils {

    /**
     * the value of index not found -1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * constructor of self
     */
    private ArrayUtils() {
    }


    /**
     * if the source is empty
     *
     * @param source source
     * @param <T> the type of source
     * @return if empty true, else false
     */
    public static <T> boolean isEmpty(final T... source) {
        return source == null || source.length == 0;
    }


    /**
     * if the source is not empty
     *
     * @param source source
     * @param <T> the type of source
     * @return if not empty true, else false
     */
    public static <T> boolean isNotEmpty(final T... source) {
        return !isEmpty(source);
    }


    /**
     * if the source has null {@code null}
     *
     * @param source source
     * @param <T> the type of source
     * @return if has null true, else false
     */
    public static <T> boolean hasNull(T... source) {
        if (isNotEmpty(source)) {
            for (T element : source) {
                if (null == element) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * get the first not null in source {@code null}
     *
     * @param source source
     * @param <T> the type of source
     * @return T the first not null string
     */
    public static <T> T firstNotNull(T... source) {
        if (isNotEmpty(source)) {
            for (final T val : source) {
                if (null != val) {
                    return val;
                }
            }
        }
        return null;
    }


    /**
     * new array of component type with input size
     *
     * @param componentType component type
     * @param newSize the new size
     * @param <T> the type of array
     * @return T null Array
     */
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    /**
     * force to cast source object to Array<br>
     * the result is to get a new array
     *
     * @param type type of source array or component type
     * @param source source object
     * @return Object[]
     * @throws NullPointerException null point exception
     * @throws IllegalArgumentException if the source is illegal
     */
    public static Object[] cast(Class<?> type, Object source) throws NullPointerException, IllegalArgumentException {
        if (null == source) {
            throw new NullPointerException("Argument [sourceObj] is null !");
        }
        if (false == source.getClass().isArray()) {
            throw new IllegalArgumentException("Argument [sourceObj] is not source !");
        }
        if (null == type) {
            return (Object[]) source;
        }

        final Class<?> componentType = type.isArray() ? type.getComponentType() : type;
        final Object[] src = (Object[]) source;
        final Object[] dest = ArrayUtils.newArray(componentType, src.length);
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    /**
     * append new element to array<br>
     * after append create a new array, do not affect the source array
     *
     * @param source source T
     * @param newElements new elements
     * @param <T> the type of array or component type
     * @return  T new array
     */
    public static <T> T[] append(T[] source, T... newElements) {
        if (isEmpty(newElements)) {
            return source;
        }

        T[] t = resize(source, source.length + newElements.length);
        System.arraycopy(newElements, 0, t, source.length, newElements.length);
        return t;
    }

    /**
     * resize the source to a new array with source class component type
     *
     * @param <T> the type of array or component type
     * @param source source array
     * @param newSize new size
     * @return T new array
     */
    public static <T> T[] resize(T[] source, int newSize) {
        return resize(source, newSize, source.getClass().getComponentType());
    }

    /**
     * resize the source to a new array with new size use input component type
     *
     * @param <T> the type of array or component type
     * @param source source array
     * @param newSize new size
     * @param componentType component type
     * @return T new array
     */
    public static <T> T[] resize(T[] source, int newSize, Class<?> componentType) {
        T[] newArray = newArray(componentType, newSize);
        if (isNotEmpty(source)) {
            System.arraycopy(source, 0, newArray, 0, Math.min(source.length, newSize));
        }
        return newArray;
    }


    /**
     * add all sources to a new array<br>
     * ignore null array
     *
     * @param sources sources of T
     * @param <T> the type of array or component type
     * @return T new array
     */
    public static <T> T[] addAll(T[]... sources) {
        if (sources.length == 1) {
            return sources[0];
        }

        int length = 0;
        for (T[] source : sources) {
            if (source == null) {
                continue;
            }
            length += source.length;
        }
        T[] result = newArray(sources.getClass().getComponentType().getComponentType(), length);

        length = 0;
        for (T[] source : sources) {
            if (source == null) {
                continue;
            }
            System.arraycopy(source, 0, result, length, source.length);
            length += source.length;
        }
        return result;
    }

    /**
     * copy the source object to dest object
     * use {@link System#arraycopy(Object, int, Object, int, int)}<br>
     *
     * @param source source object of array
     * @param srcPos source position
     * @param dest dest object of array
     * @param destPos dest position
     * @param length the length of copy
     * @return Object
     */
    public static Object copy(Object source, int srcPos, Object dest, int destPos, int length) {
        System.arraycopy(source, srcPos, dest, destPos, length);
        return dest;
    }

    /**
     * copy the source object to dest object source position 0, dest position 0
     * use {@link System#arraycopy(Object, int, Object, int, int)}<br>
     *
     * @param source source object of array
     * @param dest dest object of array
     * @param length the length of copy
     * @return Object
     */
    public static Object copy(Object source, Object dest, int length) {
        System.arraycopy(source, 0, dest, 0, length);
        return dest;
    }

    /**
     * clone the source array
     *
     * @param source source T of array
     * @param <T> the type of array or component type
     * @return  T new array
     */
    public static <T> T[] clone(T[] source) {
        if (source == null) {
            return null;
        }
        return source.clone();
    }

    /**
     * clone the source array, if source is null return <code>null</code>
     *
     * @param source source T of array
     * @param <T> the type of array or component type
     * @return T new array
     */
    public static <T> T clone(final T source) {
        if (null == source) {
            return null;
        }
        if (isArray(source)) {
            final Object result;
            final Class<?> componentType = source.getClass().getComponentType();
            if (componentType.isPrimitive()) {
                int length = Array.getLength(source);
                result = Array.newInstance(componentType, length);
                while (length-- > 0) {
                    Array.set(result, length, Array.get(source, length));
                }
            } else {
                result = ((Object[]) source).clone();
            }
            return (T) result;
        }
        return null;
    }


    /**
     * split the source byte to byte[][] with input len, the last part may less then len
     *
     * @param source source source
     * @param len the length of every part
     * @return byte[][] the split source
     */
    public static byte[][] split(byte[] source, int len) {
        int x = source.length / len;
        int y = source.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] sources = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(source, i * len, arr, 0, y);
            } else {
                System.arraycopy(source, i * len, arr, 0, len);
            }
            sources[i] = arr;
        }
        return sources;
    }

    /**
     * zip with K, V to create a  new array, refer Python zip() method<br>
     * the default is not in order
     * Example:<br>
     * keys = [a,b,c,d]<br>
     * values = [1,2,3,4]<br>
     * the result Map is  {a=1, b=2, c=3, d=4}<br>
     * if the length of K, V is not equals, use the short length
     *
     * @param keys keys array
     * @param values values array
     * @param <K> Key type
     * @param <V> Value type
     * @return the result
     */
    public static <K, V> Map<K, V> zip(K[] keys, V[] values) {
        return zip(keys, values, false);
    }

    /**
     * zip with K, V to create a  new array, refer Python zip() method<br>
     * Example:<br>
     * keys = [a,b,c,d]<br>
     * values = [1,2,3,4]<br>
     * the result Map is  {a=1, b=2, c=3, d=4}<br>
     * if the length of K, V is not equals, use the short length
     *
     * @param keys keys array
     * @param values values array
     * @param isOrder if in order, true is in order
     * @param <K> Key type
     * @param <V> Value type
     * @return the result
     */
    public static <K, V> Map<K, V> zip(K[] keys, V[] values, boolean isOrder) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }

        final int size = Math.min(keys.length, values.length);
        final Map<K, V> map = newHashMap(size, isOrder);
        for (int i = 0; i < size; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    }

    /**
     * new a HashMap for array operation
     *
     * @param size the size of HashMap, because the load factory is 0.75
     *             so the actual size is size / 0.75
     * @param isOrder is the Map Key in order,
     *                true return {@link LinkedHashMap}, false return {@link HashMap}
     * @param <K> Key type
     * @param <V> Value type
     * @return the result HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap(int size, boolean isOrder) {
        int initialCapacity = (int) (size / 0.75);
        return isOrder ? new LinkedHashMap<K, V>(initialCapacity) : new HashMap<K, V>(initialCapacity);
    }


    /**
     * get the index of source with input value, if not found return {@link #INDEX_NOT_FOUND}
     *
     * @param source source T source
     * @param dest the dest check object
     * @param <T> T array
     * @return the position of checker in source, if not found return {@link #INDEX_NOT_FOUND}
     */
    public static <T> int indexOf(T[] source, Object dest) {
        for (int i = 0; i < source.length; i++) {
            if(ObjectUtils.nullSafeEquals(dest, source[i])){
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * get the last index of source with input value,
     * if not found return {@link #INDEX_NOT_FOUND}
     *
     * @param source source T source
     * @param dest the dest check object
     * @param <T> T array
     * @return the last position of checker in source, if not found return {@link #INDEX_NOT_FOUND}
     */
    public static <T> int lastIndexOf(T[] source, Object dest) {
        for (int i = source.length-1; i >= 0; i--) {
            if(ObjectUtils.nullSafeEquals(dest, source[i])){
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * if the source T contains the dest T
     *
     * @param source source source
     * @param dest the dest
     * @param <T> the type of array or component type
     * @return if contains true, else false
     */
    public static <T> boolean contains(T[] source, T dest) {
        return indexOf(source, dest) > INDEX_NOT_FOUND;
    }


    /**
     * wrap the source object to Object[] array
     *
     * @param source the source object, can be a object array or a base array
     * @return Object[] after wrap
     * @throws CoreException the source is not a array
     */
    public static Object[] wrap(Object source) {
        if (isArray(source)) {
            try {
                return (Object[]) source;
            } catch (Exception e) {
            }
        }
        throw new CoreException(StringUtils.format("[{}] is not Array!", source.getClass()));
    }

    /**
     * if the source object is a Array
     *
     * @param source the source object
     * @return if is a array true, else false
     * @throws NullPointerException if the source is null, then<code>null</code>
     */
    public static boolean isArray(Object source) {
        if (null == source) {
            throw new NullPointerException("Object check for isArray is null");
        }
        return source.getClass().isArray();
    }

    /**
     * trans the source object to string
     *
     * @param source the source object
     * @return String the string of source object
     */
    public static String toString(Object source) {
        if (null == source) {
            return null;
        }
        if (ArrayUtils.isArray(source)) {
            try {
                return Arrays.deepToString((Object[]) source);
            } catch (Exception e) {
//                final String className = source.getClass().getComponentType().getName();
//                switch (className) {
//                    case "long":
//                        return Arrays.toString((long[]) source);
//                    case "int":
//                        return Arrays.toString((int[]) source);
//                    case "short":
//                        return Arrays.toString((short[]) source);
//                    case "char":
//                        return Arrays.toString((char[]) source);
//                    case "byte":
//                        return Arrays.toString((byte[]) source);
//                    case "boolean":
//                        return Arrays.toString((boolean[]) source);
//                    case "float":
//                        return Arrays.toString((float[]) source);
//                    case "double":
//                        return Arrays.toString((double[]) source);
//                    default:
//                        throw new CoreException(e);
//                }
            }
        }
        return source.toString();
    }

    /**
     * join the source T with input spt
     *
     * @param source source T source
     * @param spt separator
     * @param <T> the type of T
     * @return String the join string of source with spt
     */
    public static <T> String join(T[] source, CharSequence spt) {
        if (null == source) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (T item : source) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(spt);
            }
            if (ArrayUtils.isArray(item)) {
                sb.append(join(ArrayUtils.wrap(item), spt));
            } else if (item instanceof Iterator<?>) {
                sb.append(CollectionUtils.join((Iterator<?>) item, spt));
            } else {
                sb.append(item);
            }
        }
        return sb.toString();
    }


    /**
     * trans {@link ByteBuffer} source to byte[]
     *
     * @param source the source byte buffer{@link ByteBuffer}
     * @return byte[] byte array
     * @since 1.6
     */
//    public static byte[] toArray(ByteBuffer source) {
//        if (false == source.hasArray()) {
//            int oldPosition = source.position();
//            source.position(0);
//            int size = source.limit();
//            byte[] buffers = new byte[size];
//            source.get(buffers);
//            source.position(oldPosition);
//            return buffers;
//        } else {
//            return Arrays.copyOfRange(source.array(), source.position(), source.limit());
//        }
//    }

}
