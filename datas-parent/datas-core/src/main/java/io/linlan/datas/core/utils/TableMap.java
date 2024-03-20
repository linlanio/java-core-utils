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
package io.linlan.datas.core.utils;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * Filename:TableMap.java
 * Desc: 表格MAP对象工具类
 *
 * @author Linlan
 * CreateTime:2020/12/20 22:15
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class TableMap extends HashMap<String, String> implements Serializable {

    @Override
    public String get(Object key) {
        return super.get(key.toString().replace("\"", ""));
    }
}
