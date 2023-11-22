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

/**
 *
 * Filename:Initializing.java
 * Desc: 初始化接口类，在属性设置后进行操作
 *
 * @author linlan of linlan.io
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/12/20 22:13
 *
 * @version 1.0
 * @since 1.0
 *
 */
public interface Initializing {

    /** 在属性配置后进行相关操作，提供给数据源实现类进行实现
     *
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;

}
