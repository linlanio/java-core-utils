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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.*;

/**
 * map utils for json convert
 * Filename:JsonMapUtils.java
 * Desc: Map JSON utils include analysisToMaps, analysisToMap, toJsonObj, mapToObject,
 *
 * @author Linlan
 * CreateTime:2020-06-28 8:47 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class JsonMapUtils {

    /**
     * 将JSON格式的string转成map对象
     * @param jsonStr 输入的JSON 字符串
     * @return {@link Map<String, Object>}
     */
    public static Map<String, Object> analysisToMaps(String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Iterator it = jsonObject.entrySet().iterator();
        Map<String, Object> result = new HashMap<String, Object>();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            Object objVal = entry.getValue();
            if(objVal instanceof Integer || objVal instanceof Double){
                result.put(entry.getKey(), entry.getValue()+"");
            }else if(objVal instanceof JSONObject){
                result.put(entry.getKey(), (JSONObject) entry.getValue());
            }else if(objVal instanceof JSONArray){
                result.put(entry.getKey(), (JSONArray) entry.getValue());
            }else if(objVal instanceof Boolean){
                result.put(entry.getKey(), (Boolean) entry.getValue());
            }else if(objVal instanceof BigDecimal){
                result.put(entry.getKey(), (BigDecimal) entry.getValue());
            }else{
                result.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return result;
    }

    /**
     * map转JSONObject
     * @param map map对象
     * @return {@link JSONObject}
     */
    public static JSONObject toJsonObj(Map<String, Object> map) {
        JSONObject resultJson = new JSONObject();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            resultJson.put(key, map.get(key));
        }
        return resultJson;
    }


    /**
     * 将string转成map对象
     * @param jsonStr
     * @return {@link Map<String, String>}
     */
    public static Map<String, String> analysisToMap(String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Iterator it = jsonObject.entrySet().iterator();
        Map<String, String> result = new HashMap<String, String>();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            Object objVal = entry.getValue();
            if(objVal instanceof Integer || objVal instanceof Double){
                result.put(entry.getKey(), entry.getValue()+"");
            }else{
                result.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return result;
    }

    /** 格式化返回结果
     * @param dataStr 包含数据的JSON字符串
     * @param confStr 配置JSON字符串，控制仅返回数据中的有效字段
     * @return {@link Map<String, String>}
     */
    public static Map<String, String> analysisDataToMap(String dataStr, String confStr){
        Map<String, String> dataStrMap = analysisToMap(dataStr);
        Map<String, String> confMap = analysisToMap(confStr);
        for(Map.Entry<String, String> confEntry : confMap.entrySet()){
            String entryVal = confEntry.getValue();
            String dataVal = dataStrMap.get(entryVal);
            confEntry.setValue(dataVal);
        }
        return confMap;
    }


    /**
     * 匹配对应的数据
     * @param dataJson
     * @param confJson
     * @return
     */
    public static JSONObject analysisDataToMap(JSONObject dataJson, JSONObject confJson){
        JSONObject result = new JSONObject();
        if(null!=confJson){
            result.putAll(confJson);
            for(Map.Entry<String, Object> confEntry : result.entrySet()){
                if(confEntry.getValue() instanceof  String){//string类型
                    String entryVal = (String) confEntry.getValue();
                    Object dataVal = dataJson.get(entryVal);
                    if(dataVal instanceof String){
                        dataVal = ((String) dataVal).replace("%", "");
                    }
                    confEntry.setValue(dataVal);
                }
            }
        }
        return result;
    }

    /** map转对象. 避免循环取值赋值.
     * @param params map 对象的参数组
     * @param clazz 用于控制对象转换的类名称
     * @return
     */
    public static Object mapToObject( Map<String, Object> params, Class clazz){
        String obj = JSON.toJSONString(params);
        JSONObject jsonObject = JSONObject.parseObject(obj);
        Object result = JSONObject.toJavaObject(jsonObject, clazz);
        return result;
    }

    /**
     * 对象转Map
     *
     * @param source bean对象
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object source) {
        Map<String, Object> result = new ObjectMapper().convertValue(source, Map.class);
        return result;
    }

}
