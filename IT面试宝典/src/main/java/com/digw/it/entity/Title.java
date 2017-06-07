package com.digw.it.entity;

import java.util.ArrayList;

/**
 * digw创建于17-6-4.
 */

public class Title {
    private int id;
    private String name;
    private ArrayList<SubTitle> subTitles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SubTitle> getSubTitles() {
        return subTitles;
    }

    public void setSubTitles(ArrayList<SubTitle> subTitles) {
        this.subTitles = subTitles;
    }
}
