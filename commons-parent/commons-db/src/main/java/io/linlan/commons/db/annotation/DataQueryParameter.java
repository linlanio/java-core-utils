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
 * the class of data query parameter annotation
 * Filename:QueryParameter.java
 * Desc: Query parameter annotation for common use
 * 数据查询可以输入条件或参数，包括显示方式、控件等参数
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-08-07 9:32 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataQueryParameter {

    /** Display query parameter name of input in the web page
     * @return label
     */
    String label();

    /** the query parameter type
     * @return Input type
     */
    Type type();

    /** the order of parameters input, the default is 0
     * @return the order
     */
    int order() default 0;

    /** the init value of query parameter
     * @return the default is ""
     */
    String value() default "";

    /** the place holder format of query parameter
     * @return the default is ""
     */
    String placeholder() default "";

    /** the options of query parameter
     * @return the default is "N/A"
     */
    String[] options() default "N/A";

    /** get the options method name
     * 获取options数组的方法名称
     */
    String optionsMethod() default "";

    /** if need check of query parameter
     * @return the default is false
     */
    boolean checked() default false;

    /** if required of query parameter
     * @return the default is false
     */
    boolean required() default false;

    /** the page type, all,test,dataset,widget, can be split by , the default is all
     * @return the default is all
     */
    String pageType() default "all";



    /**
     * the input type of query parameter
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
         * textarea2
         */
        TextArea2("textarea2"),
        /**
         * number
         */
        Number("number"),
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