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
package io.linlan.commons.core.lang;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Filename:BasicType.java
 * Desc:基本变量类型的枚举
 * 基本类型枚举包括原始类型和包装类型
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-07-30 11:41 AM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public enum BasicType {
    BYTE,
    SHORT,
    INT,
    INTEGER,
    LONG,
    DOUBLE,
    FLOAT,
    BOOLEAN,
    CHAR,
    CHARACTER,
    STRING;

    /** 原始类型为Key，包装类型为Value，例如： int.class =》 Integer.class. */
    public static final Map<Class<?>, Class<?>> wrapMap = new HashMap<Class<?>, Class<?>>(8);
    /** 包装类型为Key，原始类型为Value，例如： Integer.class =》 int.class. */
    public static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<Class<?>, Class<?>>(8);

    static {
        wrapMap.put(Boolean.class, boolean.class);
        wrapMap.put(Byte.class, byte.class);
        wrapMap.put(Character.class, char.class);
        wrapMap.put(Double.class, double.class);
        wrapMap.put(Float.class, float.class);
        wrapMap.put(Integer.class, int.class);
        wrapMap.put(Long.class, long.class);
        wrapMap.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : wrapMap.entrySet()) {
            primitiveMap.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 原始类转为包装类，非原始类返回原类
     * @param clazz 原始类
     * @return 包装类
     */
    public static Class<?> wrap(Class<?> clazz){
        if(null == clazz || false == clazz.isPrimitive()){
            return clazz;
        }
        Class<?> result = primitiveMap.get(clazz);
        return (null == result) ? clazz : result;
    }

    /**
     * 包装类转为原始类，非包装类返回原类
     * @param clazz 包装类
     * @return 原始类
     */
    public static Class<?> unWrap(Class<?> clazz){
        if(null == clazz || clazz.isPrimitive()){
            return clazz;
        }
        Class<?> result = wrapMap.get(clazz);
        return (null == result) ? clazz : result;
    }
}
