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
package io.linlan.datas.core.provider.config;

import java.util.Iterator;

/**
 * 
 * Filename:ConfigComponent.java
 * Desc:配置组件类，约定配置组件的add、pring、remove、iterator方法和两个主要属性获取方法，name和description
 *
 * @author Linlan
 * CreateTime:2020/12/20 22:13
 *
 * @version 1.0
 * @since 1.0
 *
 */
public abstract class ConfigComponent {

    /** 获取配置组件的名称
     * @return name
     */
    public String getName() {
        throw new UnsupportedOperationException();
    }

    /** 获取配置组件的描述
     * @return description
     */
    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    /** 给组件添加对象
     * @param ConfigComponent
     */
    public void add(ConfigComponent ConfigComponent) {
        throw new UnsupportedOperationException();
    }

    /**
     * 打印当前组件信息
     */
    public void print() {
        throw new UnsupportedOperationException();
    }

    /** 删除组件对象中某个组件
     * @param ConfigComponent
     */
    public void remove(ConfigComponent ConfigComponent) {
        throw new UnsupportedOperationException();
    }

    /** 组件对象迭代查找
     * @return
     */
    public Iterator<ConfigComponent> getIterator() {
        throw new UnsupportedOperationException();
    }
}
