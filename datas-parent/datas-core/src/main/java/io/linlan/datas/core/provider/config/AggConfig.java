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
 * Filename:AggConfig.java
 * Desc: 聚合配置类，配置信息包括四个内容，用于约定需要进行数据查询和统计分析的要素及条件
 *
 * @author Linlan
 * CreateTime:2020/12/20 22:11
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class AggConfig {

    /**
     * 行信息，保存行的维度类信息
     */
    private List<DimensionConfig> rows;
    /**
     * 列信息，保存列的维度类信息
     */
    private List<DimensionConfig> columns;
    /**
     * 过滤器，通过ConfigComponent对象进行配置扩展和规范
     */
    private List<ConfigComponent> filters;
    /**
     * 值信息，保存值的配置类信息
     */
    private List<ValueConfig> values;

    public List<DimensionConfig> getRows() {
        return rows;
    }

    public void setRows(List<DimensionConfig> rows) {
        this.rows = rows;
    }

    public List<DimensionConfig> getColumns() {
        return columns;
    }

    public void setColumns(List<DimensionConfig> columns) {
        this.columns = columns;
    }

    public List<ConfigComponent> getFilters() {
        return filters;
    }

    public void setFilters(List<ConfigComponent> filters) {
        this.filters = filters;
    }

    public List<ValueConfig> getValues() {
        return values;
    }

    public void setValues(List<ValueConfig> values) {
        this.values = values;
    }
}
