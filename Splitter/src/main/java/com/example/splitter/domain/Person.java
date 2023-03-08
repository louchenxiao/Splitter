package com.example.splitter.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
    private String name;

    private List<Integer> groupIdList;

    public Person(String name) {
        this.groupIdList = new ArrayList<>();
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<Integer> groupIdList) {
        this.groupIdList = groupIdList;
    }

    @Override
    public String toString() {
        return name;
    }
}
