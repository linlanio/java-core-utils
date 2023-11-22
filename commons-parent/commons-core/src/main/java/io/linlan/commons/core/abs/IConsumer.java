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
package io.linlan.commons.core.abs;

/**
 * the interface of Consumer to define
 * Filename:IConsumer.java
 * Desc:the interface of Consumer, to define accept method for map operations
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * Createtime 2017/7/12 9:08 PM
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public interface IConsumer {
    /**
     * the implement of interface to accept ken and value for map
     * @param key K
     * @param value V
     */
    void accept(String key, Object value);
}
