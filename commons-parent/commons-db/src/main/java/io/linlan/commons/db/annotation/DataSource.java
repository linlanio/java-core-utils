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
 * the class of data source annotation
 * Filename:DataSource.java
 * Desc: Data source annotation for common use
 * data source name to define different data source from
 * oracle, mysql, db2, dm, h2 etc.
 * 数据源名称，数据源区分的标记，后续可增加其他条件
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-08-09 10:43 AM
 *
 * @version 1.0
 * @since 1.0
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    /** the name of datasource
     * @return the default is ""
     */
    String name() default "";
}
