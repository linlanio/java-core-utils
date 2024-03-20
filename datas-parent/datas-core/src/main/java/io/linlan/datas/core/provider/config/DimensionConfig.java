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
package io.linlan.datas.core.provider.config;

import java.util.List;

/**
 * 
 * Filename:DimensionConfig.java
 * Desc:维度配置类，对配置组件类进行扩展，增加五个新属性
 *
 * @author Linlan
 * CreateTime:2020/12/20 22:13
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class DimensionConfig extends ConfigComponent {
    /**
     * 维度配置类ID
     */
    private String id;
    /**
     * 维度列名称
     */
    private String columnName;
    /**
     * 信息过滤类型
     */
    private String filterType;
    /**
     * 过滤后的值列表
     */
    private List<String> values;
    /**
     * 自定义其他属性
     */
    private String custom;

    public String getColumnName() {
        return columnName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
