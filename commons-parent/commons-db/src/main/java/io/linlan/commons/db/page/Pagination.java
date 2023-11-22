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
package io.linlan.commons.db.page;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Page class to provide for data view usage
 * Filename:PaginationHib.java
 * Desc:the page class for data view, to get and set page info.
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-07-11 8:01 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class Pagination<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数 the total count
     */
    private int totalCount;
    /**
     * 每页记录数 the page size
     */
    private int pageSize;
    /**
     * 总页数 the total page
     */
    private int totalPage;
    /**
     * 当前页数 the current page
     */
    private int currPage = 1;
    /**
     * 列表数据 the list
     */
    private List<T> list;

    public Pagination() {

    }
    /**
     * the constructor of Pagination
     * @param pageSize    每页记录数, the page size
     * @param currPage    当前页数, the current page
     */
    public Pagination(int pageSize, int currPage) {
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }

    /**
     * the constructor of Pagination
     * @param totalCount  总记录数, the total count
     * @param pageSize    每页记录数, the page size
     * @param currPage    当前页数, the current page
     */
    public Pagination(int totalCount, int pageSize, int currPage) {
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalCount = totalCount;
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }

    /**
     * the constructor of Pagination
     * @param list        列表数据, the list
     * @param totalCount  总记录数, the total count
     * @param pageSize    每页记录数, the page size
     * @param currPage    当前页数, the current page
     */
    public Pagination(List<T> list, int totalCount, int pageSize, int currPage) {
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalCount = totalCount;
        this.list = list;
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }

    /**
     * the constructor of Pagination
     * @param list        列表数据, the list
     * @param page        分页对象, the page
     */
    public Pagination(List<T> list, Page page) {
        this.pageSize = page.getPageSize();
        this.currPage = page.getPageNum();
        this.totalCount = (int)page.getTotal();
        this.list = list;
        this.totalPage = page.getPages();
    }

    /**
     * the constructor of Pagination
     * @param page        分页对象, the page
     */
    public Pagination(Page<T> page) {
        this.pageSize = page.getPageSize();
        this.currPage = page.getPageNum();
        this.totalCount = (int)page.getTotal();
        this.list = page.getResult();
        this.totalPage = page.getPages();
    }

    /**
     * Gets the first result.
     *
     * @return the first result
     */
    public int getFirst() {
        return (currPage - 1) * totalCount;
    }

    /**
     * @return get the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount set totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return get the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize set the pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return get the totalPage
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * @param totalPage set the totalPage
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @return get the currPage
     */
    public int getCurrPage() {
        return currPage;
    }

    /**
     * @param currPage set the currPage
     */
    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    /**
     * @return get the list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list set the list
     */
    public void setList(List<T> list) {
        this.list = list;
    }
}
