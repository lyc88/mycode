package com.whyuan.$6utils.log;

/**
 * <p>Author: huyisen@gmail.com
 * <p>Date: 2017-04-12 13:33
 * <p>Version: 1.0
 */
public class Field {
    private String name;
    private String type;

    public Field(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
