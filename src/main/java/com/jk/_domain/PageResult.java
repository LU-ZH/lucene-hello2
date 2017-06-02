package com.jk._domain;

import java.util.List;

/**
 * Created by dell on 2017/6/2.
 */
public class PageResult {

    private Integer count; //总记录数
    private List list; //一段数据

    public PageResult(Integer count, List list) {
        this.count = count;
        this.list = list;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
