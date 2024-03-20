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
package io.linlan.datas.core.utils;

import io.linlan.datas.core.provider.config.DimensionConfig;
import io.linlan.datas.core.provider.config.ValueConfig;

/**
 *
 * Filename:H2SyntaxHelper.java
 * Desc:
 *
 * @author Linlan
 * CreateTime:2020/12/20 22:10
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class H2SyntaxHelper extends SqlSyntaxHelper {

    @Override
    public String getProjectStr(DimensionConfig config) {
        return SqlHelper.surround(super.getProjectStr(config), "`");
    }

    @Override
    public String getAggStr(ValueConfig vConfig) {
        String aggExp = SqlHelper.surround(vConfig.getColumn(), "`");
        switch (vConfig.getAggType()) {
            case "sum":
                return "SUM(f_todouble(" + aggExp + "))";
            case "avg":
                return "AVG(f_todouble(" + aggExp + "))";
            case "max":
                return "MAX(" + aggExp + ")";
            case "min":
                return "MIN(" + aggExp + ")";
            case "distinct":
                return "COUNT(DISTINCT " + aggExp + ")";
            default:
                return "COUNT(" + aggExp + ")";
        }
    }
}
