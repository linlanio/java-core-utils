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
package io.linlan.commons.db.annotation;

import java.lang.annotation.*;

/**
 * the class of data filter annotation
 * Filename:DataFilter.java
 * Desc: Data filter annotation for common use
 * 数据过滤的名称，可设置和指定表名、表别名或拥有者
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-08-06 10:43 AM
 *
 * @version 1.0
 * @since 1.0
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {

    /**
     * the table alias
     * if define tablename as u/r/t in SQL,
     * can filter to enforce action privilege
     * @return the default is ""
     */
    String tableAlias() default "";


    /**
     * the privilege of data get
     * if true, the user can search owner data
     * @return the default is true
     */
    boolean owner() default true;
}
