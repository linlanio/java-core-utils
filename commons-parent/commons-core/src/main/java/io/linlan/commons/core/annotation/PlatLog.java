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
package io.linlan.commons.core.annotation;

import java.lang.annotation.*;

/**
 * the class of plat log annotation
 * Filename:PlatLog.java
 * Desc:the plat log annotation for common use
 *
 * CreateTime:2020-07-08 11:45 AM
 *
 * @version 1.0
 * @since 1.0
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PlatLog {

    /**
     * @return the default is ""
     */
    String value() default "";

}
