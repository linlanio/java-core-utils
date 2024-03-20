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
package io.linlan.commons.db.view;

import com.github.pagehelper.BoundSqlInterceptor;
import com.github.pagehelper.PageHelper;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Filename:Page
 * Desc: 页面注释重新定义，解决显示应用注释不完整的问题.
 *
 * @author linlan
 * CreateTime:2023-09-18 8:01 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class PageView<E> extends ArrayList<E> implements Closeable {
    private static final long serialVersionUID = 1L;
    /**
     * 页面记录数
     */
    private int size;
    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 每个页面大小，默认为10
     */
    private int pageSize;
    /**
     * 起始行
     */
    private long startRow;
    /**
     * 结束行
     */
    private long endRow;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 包含count查询
     */
    private boolean count = true;
    /**
     * 分页合理化
     */
    private Boolean reasonable;
    /**
     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
     */
    private Boolean pageSizeZero;
    /**
     * 进行count查询的列名
     */
    private String countColumn;
    /**
     * 排序
     */
    private String orderBy;
    /**
     * 只增加排序
     */
    private boolean orderByOnly;
    /**
     * sql拦截处理
     */
    private BoundSqlInterceptor boundSqlInterceptor;
    /**
     * 处理器链，可以控制是否继续执行
     */
    private transient BoundSqlInterceptor.Chain chain;


    @Override
    public void close() throws IOException {
        PageHelper.clearPage();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getStartRow() {
        return startRow;
    }

    public void setStartRow(long startRow) {
        this.startRow = startRow;
    }

    public long getEndRow() {
        return endRow;
    }

    public void setEndRow(long endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public Boolean getReasonable() {
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }

    public Boolean getPageSizeZero() {
        return pageSizeZero;
    }

    public void setPageSizeZero(Boolean pageSizeZero) {
        this.pageSizeZero = pageSizeZero;
    }

    public String getCountColumn() {
        return countColumn;
    }

    public void setCountColumn(String countColumn) {
        this.countColumn = countColumn;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isOrderByOnly() {
        return orderByOnly;
    }

    public void setOrderByOnly(boolean orderByOnly) {
        this.orderByOnly = orderByOnly;
    }

    public BoundSqlInterceptor getBoundSqlInterceptor() {
        return boundSqlInterceptor;
    }

    public void setBoundSqlInterceptor(BoundSqlInterceptor boundSqlInterceptor) {
        this.boundSqlInterceptor = boundSqlInterceptor;
    }

    public BoundSqlInterceptor.Chain getChain() {
        return chain;
    }

    public void setChain(BoundSqlInterceptor.Chain chain) {
        this.chain = chain;
    }
}
