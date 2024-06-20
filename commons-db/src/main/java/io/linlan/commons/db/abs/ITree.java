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
package io.linlan.commons.db.abs;

/**
 *
 * Filename:ITree.java
 * Desc:
 *
 * CreateTime:2020-08-06 11:39 AM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public interface ITree<T extends Number> {
    /**
     * DEF_LEFT_NAME:默认树左边属性名称
     *
     * @since Ver 1.0
     */
    String DEF_LEFT_NAME = "lft";
    /**
     * DEF_RIGHT_NAME:默认树右边属性名称
     *
     * @since Ver 1.0
     */
    String DEF_RIGHT_NAME = "rgt";
    /**
     * DEF_PARENT_NAME:默认父节点属性名称
     *
     * @since Ver 1.0
     */
    String DEF_PARENT_NAME = "parent";
    /**
     * ENTITY_ALIAS:实体类别名
     *
     * @since Ver 1.0
     */
    String ENTITY_ALIAS = "bean";

    /**
     * Gets the lft name.
     *
     * @return the lft name
     */
    String getLftName();

    /**
     * Gets the rgt name.
     *
     * @return the rgt name
     */
    String getRgtName();

    /**
     * Gets the parent name.
     *
     * @return the parent name
     */
    String getParentName();

    /**
     * Gets the lft.
     *
     * @return the lft
     */
    T getLft();

    /**
     * Sets the lft.
     *
     * @param lft
     *            the new lft
     */
    void setLft(T lft);

    /**
     * Gets the rgt.
     *
     * @return the rgt
     */
    T getRgt();

    /**
     * Sets the rgt.
     *
     * @param rgt
     *            the new rgt
     */
    void setRgt(T rgt);

    /**
     * Gets the parent id.
     *
     * @return the parent id
     */
    T getParentId();

    /**
     * Gets the id.
     *
     * @return the id
     */
    T getId();

    /**
     * Gets the tree method, 树结构方法 condition.
     *
     * @return the tree method, 树结构方法 condition
     */
    String getTreeCondition();
}
