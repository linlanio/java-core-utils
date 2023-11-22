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
package io.linlan.datas.core.provider.config;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Filename:CompositeConfig.java
 * Desc: 复合配置类，对配置组件类进行扩展，增加类型丰富
 *
 * @author linlan of linlan.io
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/12/20 22:12
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class CompositeConfig extends ConfigComponent {

    /**
     * 类型
     */
    private String type;

    /**
     * 配置组件列表
     */
    private ArrayList<ConfigComponent> configComponents = new ArrayList<ConfigComponent>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ConfigComponent> getConfigComponents() {
        return configComponents;
    }

    public void setConfigComponents(ArrayList<ConfigComponent> configComponents) {
        this.configComponents = configComponents;
    }

    @Override
    public Iterator<ConfigComponent> getIterator() {
        return configComponents.iterator();
    }
}
