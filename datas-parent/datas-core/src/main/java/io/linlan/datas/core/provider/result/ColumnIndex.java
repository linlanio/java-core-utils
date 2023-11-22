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
package io.linlan.datas.core.provider.result;

import io.linlan.datas.core.provider.config.DimensionConfig;
import io.linlan.datas.core.provider.config.ValueConfig;

/**
 *
 * Filename:ColumnIndex.java
 * Desc: 列信息结果类，包括序号、聚合类型和名称
 *
 * @author linlan of linlan.io
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/12/20 22:12
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class ColumnIndex {

    /**
     * 结果序号
     */
    private int index;
    /**
     * 聚合类型，包括SUM/MIN/MAX/AVG/COUNT/DISTINCT
     */
    private String aggType;
    /**
     * 结果名称
     */
    private String name;

    public static ColumnIndex fromDimensionConfig(final DimensionConfig dimensionConfig) {
        ColumnIndex columnIndex = new ColumnIndex();
        columnIndex.setName(dimensionConfig.getColumnName());
        return columnIndex;
    }

    public static ColumnIndex fromValueConfig(final ValueConfig valueConfig) {
        ColumnIndex columnIndex = new ColumnIndex();
        columnIndex.setName(valueConfig.getColumn());
        columnIndex.setAggType(valueConfig.getAggType());
        return columnIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAggType() {
        return aggType;
    }

    public void setAggType(String aggType) {
        this.aggType = aggType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
