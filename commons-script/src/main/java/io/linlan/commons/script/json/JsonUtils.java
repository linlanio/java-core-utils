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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.linlan.commons.core.StringUtils;
import io.linlan.commons.core.io.FileUtils;
import io.linlan.commons.script.ScriptException;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * this is a utils of JSON to provide many methods from org.json
 * Filename:JsonUtils.java
 * Desc:the JSON utils in script operations, to create, parse, trans utils
 *
 * Createtime 2020/7/7 18:01
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class JsonUtils {

    /**
     * new a JSONObject
     * @return {@link JSONObject}
     */
    public static JSONObject newJO(){
        return new JSONObject();
    }


    /**
     * new a JSONArray
     * @return {@link JSONArray}
     */
    public static JSONArray newJA(){
        return new JSONArray();
    }

    /** 象
     * JSON string parse to JSONObject
     * @param source JSON string
     * @return {@link JSONObject}
     */
    public static JSONObject parseJO(String source) {
        return JSONObject.parseObject(source);
    }

    /** 20211209，增加方法，兼容Object对象转换为JSONObject对
     * JSON字符串转JSONObject对象<br>
     * 此方法会忽略空值，但是对JSON字符串不影响
     *
     * @param obj Bean对象或者Map
     * @return JSONObject
     */
    public static JSONObject parseObj(Object obj) {
        return JSONObject.parseObject(obj.toString());
    }

    /**
     * Map parse to JSONObject
     * @param map {@link StringMap}
     * @return {@link JSONObject}
     */
    public static JSONObject parseFromMap(StringMap map){
        return JSONObject.parseObject(map.map().toString());
    }

    /**
     * Map parse to JSONObject
     * @param map {@link Map}
     * @return {@link JSONObject}
     */
    public static JSONObject parseFromMap(Map<?, ?> map){
        return JSONObject.parseObject(map.toString());
    }

    /**
     * parse from a file and charset to a JSONObject
     *
     * @param file JSON format file
     * @return {@link JSONObject}
     * @throws ScriptException exception
     */
    public static JSONObject parseJOFromFile(File file) throws
            ScriptException
    {
        return parseJO(FileUtils.create(file).toString());
    }


    /**
     * JSON string parse to JSONArray
     * @param source JSON string
     * @return {@link JSONArray}
     */
    public static JSONArray parseJA(String source) {
        return JSONArray.parseArray(source);
    }

    /**
     * parse from a file and charset to a JSONArray
     *
     * @param file JSON format file
     * @return {@link JSONArray}
     * @throws ScriptException exception
     */
    public static JSONArray parseJAFromFile(File file) throws ScriptException {
        return parseJA(FileUtils.create(file).toString());
    }

    /**
     * This method serializes the specified object into its equivalent Json representation.
     * This method should be used when the specified object is not a generic type. This method uses
     * {@link Class#getClass()} to get the type for the specified object, but the
     * {@code getClass()} loses the generic type information because of the Type Erasure feature
     * of Java. Note that this method works fine if the any of the object fields are of generic type,
     * just the object itself should not be of a generic type. If the object is of generic type, use
     * {@link #toJson(Object)} instead.
     *
     * @param source the object for which Json representation is to be created setting for Gson
     * @return Json representation of {@code source}.
     */
    public static String toJson(Object source) {
        return encode(source);
    }

    /**
     * This method serializes the specified object, including those of generic types, into its
     * equivalent Json representation. This method must be used if the specified object is a generic
     * type. For non-generic objects, use {@link #toJson(Object)} instead.
     *
     * @param source the object for which JSON representation is to be created
     * @return Json representation of {@code source}
     */
    public static String encode(Object source) {
        return JSON.toJSONString(source);
    }

    /** StringMap trans to string
     * @param map a StringMap
     * @return {@link String}
     */
    public static String encode(StringMap map) {
        return JSON.toJSONString(map.map());
    }

    /**
     * JSONObject trans to string
     * @param source a JSONObject
     * @return {@link String}
     */
    public static String encode(JSONObject source){
        return source.toString();
    }

    /**
     * JSONArray trans to string
     * @param source a JSONArray
     * @return {@link String}
     */
    public static String encode(JSONArray source){
        return source.toString();
    }


    /**
     * direct to deal with " to \"<br>
     * in order to view in HTML, direct &lt;/ to &lt;\/<br>
     * JSON string can not include \ " or other special character
     *
     * @param string a string
     * @return A String correctly formatted for insertion in a JSONObject text.
     */
    public static String quote(String string) {
        StringWriter sw = new StringWriter();
        synchronized (sw.getBuffer()) {
            try {
                return quote(string, sw).toString();
            } catch (IOException ignored) {
                // will never happen - we are writing to a string writer
                return "";
            }
        }
    }

    /**
     * direct to deal with " to \"<br>
     * in order to view in HTML, direct &lt;/ to &lt;\/<br>
     * JSON string can not include \ " or other special character
     *
     * @param string a string
     * @param writer {@link Writer}
     * @return A String correctly formatted for insertion in a JSONObject text.
     * @throws IOException IO exception
     */
    public static Writer quote(String string, Writer writer) throws IOException {
        return StringUtils.writeByWriter(string, writer);
    }

    /**
     * ResourceBundle转化为JSONObject
     * @param bundle ResourceBundle文件
     * @return {@link JSONObject}
     */
    public static JSONObject parseFromResourceBundle(ResourceBundle bundle){
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key != null) {
                copyProperty(jsonObject, key, bundle.getString(key));
            }
        }
        return jsonObject;
    }


    /**
     * property key trans to JSON <br>
     * to split the key with .
     * Go through the path, ensuring that there is a nested JSONObject for each
     * segment except the last. Add the value using the last segment's name into
     * the deepest nested JSONObject.
     *
     * @param jsonObject JSONObject
     * @param key
     * @param value
     * @return {@link JSONObject}
     */
    protected static JSONObject copyProperty(JSONObject jsonObject, Object key, Object value){
        String[] path = ((String) key).split("\\.");
        int last = path.length - 1;
        JSONObject target = jsonObject;
        for (int i = 0; i < last; i += 1) {
            String segment = path[i];
            JSONObject nextTarget = target.getJSONObject(segment);
            if (nextTarget == null) {
                nextTarget = new JSONObject();
                target.put(segment, nextTarget);
            }
            target = nextTarget;
        }
        target.put(path[last], value);
        return target;
    }

}
