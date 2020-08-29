package com.zhang.devkit;

import java.io.Serializable;

/**
 * @author
 */
public class DemoItem implements Serializable {

    public String id;
    public int num;
    public String name;

    public DemoItem(String id, int num, String name) {
        this.id = id;
        this.num = num;
        this.name = name;
    }


    @Override
    public String toString() {
        return "DemoItem{" +
                "id='" + id + '\'' +
                ", num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}
