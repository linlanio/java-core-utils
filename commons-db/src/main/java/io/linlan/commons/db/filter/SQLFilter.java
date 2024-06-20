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
package io.linlan.commons.db.filter;

import io.linlan.commons.core.CoreException;
import io.linlan.commons.core.StringUtils;

/**
 * the SQLFilter class to deal with input SQL to avoid inject
 * Filename:SQLFilter.java
 * Desc: use the sqlInject method to avoid SQL inject
 *
 * CreateTime:2020-07-29 2:02 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class SQLFilter {

    /** the sqlInject method to deal with SQL string
     * @param source the input SQL source
     * @return the security String
     */
    public static String sqlInject(String source){
        if(StringUtils.isBlank(source)){
            return null;
        }
        //get rid of '|"|;|\
        source = StringUtils.replace(source, "'", "");
        source = StringUtils.replace(source, "\"", "");
        source = StringUtils.replace(source, ";", "");
        source = StringUtils.replace(source, "\\", "");

        //转换成小写 trans SQL to lowercase
        source = source.toLowerCase();

        //非法字符 define illegal keywords
        String[] keywords = {"master ", "truncate ", "insert ", "select ", "delete ", "update ", "declare ", "alert ", "drop "};

        //判断是否包含非法字符 to deal with illegal keywords
        for(String keyword : keywords){
            if(source.indexOf(keyword) != -1){
                throw new CoreException("包含非法字符");
            }
        }

        return source;
    }
}
