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
package io.linlan.datas.core.query;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.linlan.commons.script.json.JsonBuilder;

import java.util.List;
import java.util.Map;


/**
 *
 * Filename:QueryBuilder.java
 * Desc: 提供进行查询使用的Builder
 *
 * @author linlan of linlan.io
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/12/20 22:13
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class QueryBuilder extends JsonBuilder {

    public static QueryBuilder queryContent() {
        return new QueryBuilder();
    }

    public static QueryBuilder queryContent(Map<String, Object> map) {
        return new QueryBuilder(map);
    }

    public QueryBuilder() {
    }

    public QueryBuilder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public QueryBuilder put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public QueryBuilder putJSONObject(JSONObject jsonObject) {
        super.putAll(jsonObject);
        return this;
    }

    @Override
    public QueryBuilder getJSONObject(String key) {
        return queryContent(super.getJSONObject(key));
    }

    public static QueryBuilder termQuery(String fieldName, Object value) {
        return queryContent().put("term", json(fieldName, value));
    }

    public static QueryBuilder termsQuery(String fieldName, List<? extends Object> values) {
        return queryContent().put("terms", json(fieldName, values));
    }

    public static QueryBuilder nullQuery(String fieldName, boolean isNull) {
        return boolFilter(isNull ? BoolType.MUST_NOT : BoolType.MUST, json("exists", json("field", fieldName)));
    }

    public static QueryBuilder rangeQuery(String fieldName, Object from, Object to, boolean includeLower, boolean includeUpper) {
        JsonBuilder content = json();
        if (from != null) {
            content.put(includeLower ? "gte" : "gt", from);
        }
        if (to != null) {
            content.put(includeUpper ? "lte" : "lt", to);
        }
        return queryContent().put("range", json(fieldName, content));
    }

    public static QueryBuilder rangeQuery(String fieldName, Object from, Object to) {
        return rangeQuery(fieldName, from, to, false, false);
    }

    public static QueryBuilder boolFilter(BoolType boolType, JSONArray filterArray) {
        return queryContent()
                .put("bool",
                        json(boolType.toString(), filterArray)
                );
    }

    public static QueryBuilder boolFilter(BoolType boolType, JSONObject jsonObject) {
        return queryContent()
                .put("bool",
                        json(boolType.toString(), jsonObject)
                );
    }

    public static enum BoolType {
        MUST, MUST_NOT, FILTER, SHOULD;

        public String toString() {
            String result = "should";
            switch (this) {
                case MUST:
                    result = "must";
                    break;
                case MUST_NOT:
                    result = "must_not";
                    break;
                case SHOULD:
                    result = "should";
                    break;
                case FILTER:
                    result = "filter";
                    break;
            }
            return result;
        }
    }
}
