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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the class of data source annotation
 * Filename:DataSourceParameter.java
 * Desc: Datasource parameter annotation for common use
 * 数据源连接需要设置或配置的参数，包括显示方式、控件等参数
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-08-07 9:23 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceParameter {

    /** Display Datasource parameter name of input in the web page
     * @return label
     */
    String label();

    /** the Datasource parameter type
     * @return Input type
     */
    Type type();

    /** the order of parameters input, the default is 0
     * @return the order
     */
    int order() default 0;

    /** the init value of Datasource parameter
     * @return the default is ""
     */
    String value() default "";

    /** the place holder format of Datasource parameter
     * @return the default is ""
     */
    String placeholder() default "";

    /** the options of Datasource parameter
     * @return the default is "N/A"
     */
    String[] options() default "N/A";

    /** if need check of Datasource parameter
     * @return the default is false
     */
    boolean checked() default false;

    /** if required of Datasource parameter
     * @return the default is false
     */
    boolean required() default false;

    /**
     * the input type of Datasource parameter
     */
    enum Type {

        /**
         * input box
         */
        Input("input"),
        /**
         * textarea
         */
        TextArea("textarea"),
        /**
         * password
         */
        Password("password"),
        /**
         * checkbox
         */
        Checkbox("checkbox"),
        /**
         * select
         */
        Select("select");

        /**
         * the name of type
         */
        private String name;

        /** constructor of self with input name
         * @param name the name
         */
        Type(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

    }
}
