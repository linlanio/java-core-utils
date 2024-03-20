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
package io.linlan.commons.db.mybatis;

import java.util.List;
import java.util.Map;

/**
 * Mybatis BaseDao Interface for Mybatis Dao to extends
 * Filename:MybatisBaseDao.java
 * Desc:Need to define and implement the method in annotation or
 * in XML
 *
 * CreateTime:2017-07-03 1:57 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public interface MybatisBaseDao<T> {

    /** get list of entity with input map conditions
     *
     * @param map the input conditions
     * @return the list of T
     */
    List<T> getList(Map<String, Object> map);

    /** get page of entity with input map conditions
     *
     * @param map the input conditions
     * @return the Page of T
     */
    List<T> getPage(Map<String, Object> map);

    /** find the entity by id
     *
     * @param id the id
     * @return T the entity
     */
    T findById(Object id);

    /** save entity of input t
     *
     * @param t the class of entity
     */
    void save(T t);

    /** save entity of input map
     *
     * @param map the value in map
     */
    void save(Map<String, Object> map);

    /** batch save entity of input list
     *
     * @param list the list of T
     */
    void save(List<T> list);

    /** update entity of input t
     *
     * @param t the class of entity
     * @return the result of update
     */
    int update(T t);

    /** update entity of input map
     *
     * @param map the value in map
     * @return the result of update
     */
    int update(Map<String, Object> map);

    /** save entity of input map
     *
     * @param id the id to get the entity
     * @return the result of delete
     */
    int deleteById(Object id);

    /** batch delete entity by ids
     *
     * @param ids the ids
     * @return the result of delete
     */
    int deleteByIds(Object[] ids);

    /** delete entity of input map
     *
     * @param map the value in map
     * @return the result of delete
     */
    int delete(Map<String, Object> map);

    /** get the total result
     *
     * @return total count
     */
    int getTotal();

    /** get the count by select conditions
     *
     * @param map the input select conditions
     * @return count
     */
    int getCount(Map<String, Object> map);

}

