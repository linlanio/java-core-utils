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
package io.linlan.datas.core.abs;

import io.linlan.datas.core.provider.config.AggConfig;
import io.linlan.datas.core.provider.result.AggregateResult;

/**
 *
 * Filename:Aggregatable.java
 * Desc: 聚合接口类，提供给数据源实现类进行继承
 *
 * @author linlan of linlan.io
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/12/20 22:11
 *
 * @version 1.0
 * @since 1.0
 *
 */
public interface Aggregatable {

    /**
     * The data provider that support DataSource side Aggregation must implement this method.
     * 查询维度的值，可以进行维度展示的值
     *
     * @param columnName    列名称
     * @param config        聚合配置类
     * @return 维度输出数组
     */
    String[] queryDimVals(String columnName, AggConfig config) throws Exception;

    /**
     * The data provider that support DataSource side Aggregation must implement this method.
     * 查询列数组，可设置变量reload，为true时表示字段加载
     * @param reload        重新加载，true表示自动重新加载
     * @return  列数组
     */
    String[] getColumn(boolean reload) throws Exception;

    /**
     * The data provider that support DataSource side Aggregation must implement this method.
     * 通过聚合配置类的属性，得到聚合查询后的结果类
     *
     * @param ac    aggregate configuration
     * @return      {@link AggregateResult}
     */
    AggregateResult queryAggData(AggConfig ac) throws Exception;

    /** view the agg data result
     *  通过聚合配置类查看可以进行浏览的数据，该方法后续进行扩展
     * @param ac
     * @return
     * @throws Exception
     */
    default String viewAggDataQuery(AggConfig ac) throws Exception {
        return "Not Support";
    }

}
