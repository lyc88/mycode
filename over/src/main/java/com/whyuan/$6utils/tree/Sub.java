package com.whyuan.$6utils.tree;

import java.util.List;

/**
 *
 * @author: gavin <gavinyi@aliyun.com>
 * @date: 2017/8/1
 * @version: 1.0
 */
public class Sub {
    private String name;
    private List<Sub> sub;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sub> getSub() {
        return sub;
    }

    public void setSub(List<Sub> sub) {
        this.sub = sub;
    }
}
