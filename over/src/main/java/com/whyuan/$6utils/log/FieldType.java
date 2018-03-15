package com.whyuan.$6utils.log;

/**
 * <p>Author: huyisen@gmail.com
 * <p>Date: 2017-04-12 13:30
 * <p>Version: 1.0
 */
public class FieldType {

    //TODO add more schema field properties?
    private String name;

    public FieldType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
