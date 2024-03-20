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
package io.linlan.datas.core.query;

import io.linlan.commons.core.StringUtils;

/**
 *
 * Filename:SqlMethod.java
 * Desc: SQL 方法检查类
 *
 * @author Linlan
 * CreateTime:2020/12/20 22:13
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class SqlMethod {

    /** 对多个SQL部分进行合并，合并成完整的SQL信息
     * @param p
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> T coalesce(T... p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] instanceof String && StringUtils.isEmpty((String) p[i])) {
                p[i] = null;
            }
            if (null != p[i]) {
                return p[i];
            }
        }
        return null;
    }
}
