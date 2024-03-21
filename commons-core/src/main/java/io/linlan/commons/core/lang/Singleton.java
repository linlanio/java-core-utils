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
package io.linlan.commons.core.lang;

import io.linlan.commons.core.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton class to manage single object in the object pool.
 * Filename:Singleton.java
 * Desc:Singleton class to use ConcurrentHashMap pool to manage.
 *
 * Createtime 2020/7/2 8:49 PM
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public final class Singleton {
    /**
     * the base pool to manage single class
     */
    private static Map<Class<?>, Object> pool = new ConcurrentHashMap<>();

    /**
     * constructor of self
     */
    private Singleton() {

    }

    /**
     * get the single object by Singleton<br>
     * use pool to manage objects, if the object in pool then do not create
     * if the object not in pool then create<br>
     *
     * @param clazz class
     * @param params params to constructor
     * @param <T> the type of clazz
     * @return clazz object of Singleton mode
     */
    public static <T> T get(Class<T> clazz, Object... params) {
        T object = (T) pool.get(clazz);

        if(null == object) {
            synchronized(Singleton.class) {
                object = (T) pool.get(clazz);
                if(null == object) {
                    object = ClassUtils.newInstance(clazz, params);
                    pool.put(clazz, object);
                }
            }
        }

        return object;
    }

    /**
     * remove clazz from Singleton object in pool
     * @param clazz clazz
     */
    public static void remove(Class<?> clazz) {
        pool.remove(clazz);
    }

    /**
     * destroy the Singleton object and clear the pool
     */
    public static void destroy() {
        pool.clear();
    }
}
