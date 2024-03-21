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

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * a JSON builder of JSON Object utils
 * Filename:JsonBuilder.java
 * Desc: a Json Builder to create JSONObject of fastjson
 *
 * CreateTime:2020/08/20 18:32
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class JsonBuilder extends JSONObject {

    /** json method to create new JSONObject with default
     * @return JsonBuilder
     */
    public static JsonBuilder json() {
        return new JsonBuilder();
    }

    /** json method to create new JSONObject with input map
     * @param map the input map with key and value
     * @return JsonBuilder
     */
    public static JsonBuilder json(Map<String, Object> map) {
        return new JsonBuilder(map);
    }

    /** json method to create new JSONObject with input key and value
     * @param key the input key
     * @param value the input value
     * @return JsonBuilder
     */
    public static JsonBuilder json(String key, Object value) {
        return new JsonBuilder().put(key, value);
    }

    /**
     * the constructor of self
     */
    public JsonBuilder() {}

    /** the constructor of self with input map
     * @param map the input map with key and value
     */
    public JsonBuilder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public JsonBuilder put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @Override
    public JsonBuilder getJSONObject(String key) {
        return json(super.getJSONObject(key));
    }

    /** toString with input format and spaces
     * @param prettyFormat the format
     * @param spaces the space
     * @return String
     */
    public String toString(boolean prettyFormat, int spaces) {
        String result = JSONObject.toJSONString(this, prettyFormat);
        StringBuffer space = new StringBuffer();
        if (prettyFormat) {
            for (int i = 0; i < spaces; i++) {
                space.append(" ");
            }
            return result.replaceAll("\\t", space.toString());
        }
        return result;
    }

    /** toString with input format and default spaces
     * @param prettyFormat the format
     * @return String
     */
    public String toString(boolean prettyFormat) {
        return toString(prettyFormat, 0);
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return  a string representation of the object.
     */
    @Override
    public String toString() {
        return toString(true, 2);
    }
}
