/**
 * Copyright 2020-2023 the original author or Howai authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * this is a utils of JSON to provide many methods from com.google.gson
 * Filename:JsonDeUtils.java
 * Desc:the JSON utils from GSON in script operations
 * this utils include toBean, decode, encode methods
 *
 * CreateTime:2020-07-07 9:35 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class JsonDeUtils {

    /**
     * This method deserializes the specified Json into an object of the specified type. This method
     * is useful if the specified object is a generic type. For non-generic objects, use
     * {@link #fromJson(String, Class)} instead.
     *
     * @param <T> the type of the desired object
     * @param json the string from which the object is to be deserialized
     * @param typeOfT The specific genericized type of src. You can obtain this type by using the
     * {@code Collection<Foo>}, you should use:
     * <pre>
     * Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
     * </pre>
     * @return an object of type T from the string. Returns {@code null} if {@code json} is {@code null}.
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return JSON.parseObject(json, typeOfT);
    }

    /**
     * This method deserializes the specified Json into an object of the specified class. It is not
     * suitable to use if the specified class is a generic type since it will not have the generic
     * type information because of the Type Erasure feature of Java. Therefore, this method should not
     * be used if the desired type is a generic type. Note that this method works fine if the any of
     * the fields of the specified object are generics, just the object itself should not be a
     * generic type. For the cases when the object is of generic type, invoke
     * {@link #fromJson(String, Type)}.
     *
     * @param <T> the type of the desired object
     * @param json the string from which the object is to be deserialized
     * @param classOfT the class of T
     * @return an object of type T from the string. Returns {@code null} if {@code json} is {@code null}.
     * classOfT
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return JSON.parseObject(json, classOfT);
    }


    /** get the object of Class to decode json
     * @param json the input json string
     * @param classOfT the Class T to receive
     * @param <T> the T
     * @return the Class
     */
    public static <T> T decode(String json, Class<T> classOfT) {
        return fromJson(json, classOfT);
    }

    /** get the object of Class to decode json
     * @param json the input json string
     * @param field get the field string of input json
     * @param classOfT the Class T to receive
     * @param <T> the T
     * @return the Class
     */
    public static <T> T decode(String json, String field, Class<T> classOfT) {
        if (StringUtils.isNotBlank(field)) {
            //field不为空，则取出该field内的信息，通过类来进行转换
            JSONObject jsonObject = JSON.parseObject(json);
            JSONArray argsObjs = jsonObject.getJSONArray(field);
            if (argsObjs != null) {
                return (fromJson(argsObjs.get(0).toString(), classOfT));
            }
        }
        return fromJson(json, classOfT);
    }

    /** get the object of Class to decode json
     * @param json the input json string
     * @param field tree format like data/list/quota
     * @param classOfT the Class T to receive
     * @param <T> the T
     * @return the Class
     */
    public static <T> T decodeTree(String json, List<String> field, Class<T> classOfT) {
        JSONObject jsonObject = decodeTree(json, field);
        return fromJson(jsonObject.toString(), classOfT);

    }

    /** get the object of Class to decode json
     * @param json the input json string
     * @param field tree format like data/list/quota
     * @return JSONObject the JSONObject
     */
    public static JSONObject decodeTree(String json, List<String> field) {
        String str = decodeJsonTree(json, field);
        JSONObject result = null;
        if (isJsonArray(str)){
            JSONArray jrr = JsonUtils.parseJA(str);
            if (jrr != null && jrr.size() > 0) {
                    for (int i = 0; i < jrr.size(); i++){
                        result = jrr.getJSONObject(i);
                    }
            }
        }else{
            result = JsonUtils.parseJO(str);
        }
        return result;
    }


    /** get the object of Class to decode json
     * @param json the input json string
     * @param field tree format like data/list/quota
     * @return JSONObject the JSONObject
     */
    public static String decodeStringTree(String json, List<String> field) {
        if (field != null && field.size() > 0) {
            JSONObject jsonObject = JSON.parseObject(json);
            if(field.size() == 1) {
                return jsonObject.get(field.get(0)).toString();
            }else {
                List<String> newFields = field;
                field.remove(0);
                return decodeStringTree(jsonObject.get(newFields.get(0)).toString(), field);
            }
        }else{
            return json;
        }
    }

    public static String decodeJsonTree(String json, List<String> field) {
        if (field != null && field.size() > 0) {
            for (int i = 0; i < field.size(); i++){
                if(i < field.size() - 1) {
                    JSONObject jsonObject = JSON.parseObject(json);
                    json = decodeJsonTree(jsonObject.get(field.get(i)).toString(), field.get(i+1));
                }
            }
        }
        return json;
    }

    public static String decodeJsonTree(String json, String field) {
        if (StringUtils.isNotBlank(field)) {
            JSONObject jsonObject = JSON.parseObject(json);
            return jsonObject.get(field).toString();
        } else {
            return json;
        }
    }


    /** 将content内容进行Json处理，考虑到content内可能包含多条数据，采用List方式进行存储和使用
     * @param content
     * @return
     */
    private static List<JSONObject> dealToJsonList(String content) {
        List<JSONObject> resultJoList = new ArrayList<>();
        JSONObject resultJo = null;
        if (JsonDeUtils.isJsonArray(content)){
            JSONArray jrr = JsonUtils.parseJA(content);
            if (jrr != null && jrr.size() !=0){
                for (int i = 0; i < jrr.size(); i++){
                    resultJo = jrr.getJSONObject(i);
                    resultJoList.add(resultJo);
                }
            }
        }else{
            resultJo = JsonUtils.parseJO(content);
            resultJoList.add(resultJo);
        }
        return resultJoList;
    }
    /**
     * @param        json json content
     * @return        true     is array
     *                      false   is not array
     */
    public static boolean isJsonArray(String json) {

        if(StringUtils.isBlank(json))
            return false;
        StringUtils.isEmpty(json);
        try {
            JSONArray jsonStr = JSONArray.parseArray(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
