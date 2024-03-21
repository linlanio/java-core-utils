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
package io.linlan.commons.script.json;

import io.linlan.commons.core.CharsetUtils;
import io.linlan.commons.core.StringUtils;
import io.linlan.commons.core.abs.IConsumer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * String map class to provide utils for use
 * Filename:StringMap.java
 * Desc:String map utils to deal with map and provide
 * methods such as: put, putNotEmpty, putAll, formString etc.
 * the utils is for commons group packages to use
 *
 * Createtime 2020/7/12 8:27 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class StringMap {
    /**
     * map the object of all operations
     */
    private Map<String, Object> map;

    /**
     * constructor of self
     * to init this
     */
    public StringMap() {
        this(new HashMap());
    }

    /**
     * set the map
     * @param map map object to be set to this map
     */
    public StringMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * put the key and value to map object
     *
     * @param key K
     * @param value V
     * @return StringMap this
     */
    public StringMap put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    /**
     * put the key and value to map object if the value is not empty
     *
     * @param key K
     * @param value V
     * @return StringMap this
     */
    public StringMap putNotEmpty(String key, String value) {
        if (!StringUtils.isEmpty(value)) {
            map.put(key, value);
        }
        return this;
    }

    /**
     * put the key and value to map object if the value is not null
     *
     * @param key K
     * @param value V
     * @return StringMap this
     */
    public StringMap putNotNull(String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
        return this;
    }

    /**
     * put the key and value to map object if condition is true
     *
     * @param key K
     * @param value V
     * @param condition condition is true, put
     * @return StringMap this
     */
    public StringMap put(String key, Object value, boolean condition) {
        if (condition) {
            map.put(key, value);
        }
        return this;
    }

    /**
     * put all the key and value to map object from map object
     *
     * @param map Map object to have values
     * @return StringMap this
     */
    public StringMap putAll(Map<String, Object> map) {
        this.map.putAll(map);
        return this;
    }

    /**
     * put all the key and value to map object from StringMap object
     *
     * @param map StringMap object to have values
     * @return StringMap this a new StringMap object
     */
    public StringMap putAll(StringMap map) {
        this.map.putAll(map.map);
        return this;
    }

    /**
     * the size of StringMap
     * @return int size
     */
    public int size() {
        return map.size();
    }

    /**
     * get the map of StringMap
     * @return map this map
     */
    public Map<String, Object> map() {
        return this.map;
    }

    /**
     * get the value by key of this map
     * @param key K
     * @return V value of this key
     */
    public Object get(String key) {
        return map.get(key);
    }

    /**
     * accept all the consumer info from this map to consumer
     * @param consumer the implement of IConsumer
     */
    public void forEach(IConsumer consumer) {
        for (Map.Entry<String, Object> i : map.entrySet()) {
            consumer.accept(i.getKey(), i.getValue());
        }
    }


    /**
     * get the implement of IConsumer form string
     * @return String
     */
    public String formString() {
        final StringBuilder sb = new StringBuilder();
        forEach(new IConsumer() {
            private boolean notStart = false;

            public void accept(String key, Object value) {
                if (notStart) {
                    sb.append(StringUtils.AND);
                }
                try {
                    sb.append(URLEncoder.encode(key, CharsetUtils.UTF_8)).append(StringUtils.C_EQUAL)
                            .append(URLEncoder.encode(value.toString(), CharsetUtils.UTF_8));
                } catch (UnsupportedEncodingException e) {
                    throw new AssertionError(e);
                }
                notStart = true;
            }
        });
        return sb.toString();
    }

}
