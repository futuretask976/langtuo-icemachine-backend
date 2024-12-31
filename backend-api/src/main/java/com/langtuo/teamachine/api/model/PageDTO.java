package com.langtuo.teamachine.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageDTO<T> implements Serializable {
    /**
     * 每页展示的记录数
     */
    private int pageSize;

    /**
     * 当前页数，从1开始
     */
    private int pageNum;

    /**
     * 结果集
     */
    private List<T> list;

    /**
     * 总记录数量
     */
    private long total;

    public PageDTO(List<T> list, long total, int pageNum, int pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
