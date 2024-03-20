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

import io.linlan.commons.core.StringUtils;
import io.linlan.datas.core.provider.config.*;
import io.linlan.datas.core.provider.config.*;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * Filename:SqlHelper.java
 * Desc: SQL 查询帮助类
 *
 * @author Linlan
 * CreateTime:2020/12/20 22:12
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class SqlHelper {

    /**
     * 空字符串
     */
    public static final String NULL_STRING = "#NULL";
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 包含子查询
     */
    private boolean hasSubQuery;
    /**
     * 查询帮助语法检查
     */
    private SqlSyntaxHelper sqlSyntaxHelper = new SqlSyntaxHelper();

    public SqlHelper() {}

    public SqlHelper(String tableName, boolean hasSubQuery) {
        this.tableName = tableName;
        this.hasSubQuery = hasSubQuery;
    }

    public String assembleFilterSql(AggConfig config) {
        String whereStr = null;
        if (config != null) {
            Stream<DimensionConfig> c = config.getColumns().stream();
            Stream<DimensionConfig> r = config.getRows().stream();
            Stream<ConfigComponent> f = config.getFilters().stream();
            Stream<ConfigComponent> filters = Stream.concat(Stream.concat(c, r), f);
            whereStr = filterSql(filters, "WHERE");
        }
        return whereStr;
    }

    public String assembleFilterSql(Stream<ConfigComponent> filters) {
        return filterSql(filters, "WHERE");
    }

    public String assembleAggDataSql(AggConfig config) throws Exception {
        Stream<DimensionConfig> c = config.getColumns().stream();
        Stream<DimensionConfig> r = config.getRows().stream();
        Stream<ConfigComponent> f = config.getFilters().stream();
        Stream<ConfigComponent> filters = Stream.concat(Stream.concat(c, r), f);
        Stream<DimensionConfig> dimStream = Stream.concat(config.getColumns().stream(), config.getRows().stream());

        String dimColsStr = assembleDimColumns(dimStream);
        String aggColsStr = assembleAggValColumns(config.getValues().stream());

        String whereStr = filterSql(filters, "WHERE");
        String groupByStr = StringUtils.isBlank(dimColsStr) ? "" : "GROUP BY " + dimColsStr;

        StringJoiner selectColsStr = new StringJoiner(",");
        if (!StringUtils.isBlank(dimColsStr)) {
            selectColsStr.add(dimColsStr);
        }
        if (!StringUtils.isBlank(aggColsStr)) {
            selectColsStr.add(aggColsStr);
        }
        String fsql = null;
        if (hasSubQuery) {
            fsql = "\nSELECT %s \n FROM (\n%s\n) howai_view \n %s \n %s";
        } else {
            fsql = "\nSELECT %s \n FROM %s \n %s \n %s";
        }
        String exec = String.format(fsql, selectColsStr, tableName, whereStr, groupByStr);
        return exec;
    }

    private String filterSql(Stream<ConfigComponent> filterStream, String prefix) {
        StringJoiner where = new StringJoiner("\nAND ", prefix + " ", "");
        where.setEmptyValue("");
        filterStream.map(e -> separateNull(e))
                .map(e -> configComponentToSql(e))
                .filter(e -> e != null)
                .forEach(where::add);
        return where.toString();
    }

    private String configComponentToSql(ConfigComponent cc) {
        if (cc instanceof DimensionConfig) {
            return filter2SqlCondition.apply((DimensionConfig) cc);
        } else if (cc instanceof CompositeConfig) {
            CompositeConfig compositeConfig = (CompositeConfig) cc;
            String sql = compositeConfig.getConfigComponents().stream()
                    .map(e -> separateNull(e))
                    .map(e -> configComponentToSql(e))
                    .collect(Collectors.joining(" " + compositeConfig.getType() + " "));
            return "(" + sql + ")";
        }
        return null;
    }

    /**
     * Parser a single filter configuration to sql syntax
     */
    private Function<DimensionConfig, String> filter2SqlCondition = (config) -> {
        if (config.getValues().size() == 0) {
            return null;
        }

        String fieldName = sqlSyntaxHelper.getProjectStr(config);
        String v0 = sqlSyntaxHelper.getDimMemberStr(config, 0);
        String v1 = null;
        if (config.getValues().size() == 2) {
            v1 = sqlSyntaxHelper.getDimMemberStr(config, 1);
        }

        if (NULL_STRING.equals(config.getValues().get(0))) {
            switch (config.getFilterType()) {
                case "=":
                case "≠":
                    return config.getColumnName() + ("=".equals(config.getFilterType()) ? " IS NULL" : " IS NOT NULL");
            }
        }

        switch (config.getFilterType()) {
            case "=":
            case "eq":
                return fieldName + " IN (" + valueList(config) + ")";
            case "≠":
            case "ne":
                return fieldName + " NOT IN (" + valueList(config) + ")";
            case ">":
                return rangeQuery(fieldName, v0, null);
            case "<":
                return rangeQuery(fieldName, null, v0);
            case "≥":
                return rangeQuery(fieldName, v0, null, true, true);
            case "≤":
                return rangeQuery(fieldName, null, v0, true, true);
            case "(a,b]":
                return rangeQuery(fieldName, v0, v1, false, true);
            case "[a,b)":
                return rangeQuery(fieldName, v0, v1, true, false);
            case "(a,b)":
                return rangeQuery(fieldName, v0, v1, false, false);
            case "[a,b]":
                return rangeQuery(fieldName, v0, v1, true, true);
        }
        return null;
    };

    private String valueList(DimensionConfig config) {
        String resultList = IntStream.range(0, config.getValues().size())
                .boxed()
                .map(i -> sqlSyntaxHelper.getDimMemberStr(config, i))
                .collect(Collectors
                .joining(","));
        return resultList;
    }

    private String rangeQuery(String fieldName, Object from, Object to, boolean includeLower, boolean includeUpper) {
        StringBuffer result = new StringBuffer();
        result.append("(");
        final String gt = ">",
                gte = ">=",
                lt = "<",
                lte = "<=";
        if (from != null) {
            String op = includeLower ? gte : gt;
            result.append(fieldName + op + from);
        }
        if (to != null) {
            if (from != null) {
                result.append(" AND ");
            }
            String op = includeUpper ? lte : lt;
            result.append(fieldName + op + to);
        }
        result.append(")");
        return result.toString();
    }

    private String rangeQuery(String fieldName, Object from, Object to) {
        return rangeQuery(fieldName, from, to, false, false);
    }

    public static String surround(String text, String quta) {
        return quta + text + quta;
    }

    private String assembleAggValColumns(Stream<ValueConfig> selectStream) {
        StringJoiner columns = new StringJoiner(", ", "", " ");
        columns.setEmptyValue("");
        selectStream.map(vc -> sqlSyntaxHelper.getAggStr(vc)).filter(e -> e != null).forEach(columns::add);
        return columns.toString();
    }

    private String assembleDimColumns(Stream<DimensionConfig> columnsStream) {
        StringJoiner columns = new StringJoiner(", ", "", " ");
        columns.setEmptyValue("");
        columnsStream.map(g -> sqlSyntaxHelper.getProjectStr(g)).distinct().filter(e -> e != null).forEach(columns::add);
        return columns.toString();
    }

    public SqlHelper setSqlSyntaxHelper(SqlSyntaxHelper sqlSyntaxHelper) {
        this.sqlSyntaxHelper = sqlSyntaxHelper;
        return this;
    }

    public SqlSyntaxHelper getSqlSyntaxHelper() {
        return this.sqlSyntaxHelper;
    }

    public static ConfigComponent separateNull(ConfigComponent configComponent) {
        if (configComponent instanceof DimensionConfig) {
            DimensionConfig cc = (DimensionConfig) configComponent;
            if (("=".equals(cc.getFilterType()) || "≠".equals(cc.getFilterType())) && cc.getValues().size() > 1 &&
                    cc.getValues().stream().anyMatch(s -> NULL_STRING.equals(s))) {
                CompositeConfig compositeConfig = new CompositeConfig();
                compositeConfig.setType("=".equals(cc.getFilterType()) ? "OR" : "AND");
                cc.setValues(cc.getValues().stream().filter(s -> !NULL_STRING.equals(s)).collect(Collectors.toList()));
                compositeConfig.getConfigComponents().add(cc);
                DimensionConfig nullCc = new DimensionConfig();
                nullCc.setColumnName(cc.getColumnName());
                nullCc.setFilterType(cc.getFilterType());
                nullCc.setValues(new ArrayList<>());
                nullCc.getValues().add(NULL_STRING);
                compositeConfig.getConfigComponents().add(nullCc);
                return compositeConfig;
            }
        }
        return configComponent;
    }
}
