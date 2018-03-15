package com.whyuan.$6utils.tree;

/**
 *
 * @author: gavin <gavinyi@aliyun.com>
 * @date: 2017/8/1
 * @version: 1.0
 */
public class Place {
    private String name;
    private String word;
    private String ID;
    private String parentID;
    private String layer;
    private String not;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }
}
