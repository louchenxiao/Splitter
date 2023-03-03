package com.example.splitter.domain;

import java.util.List;
import java.util.Objects;

public class Person {
    private String name;

    private List<Integer> groupIdList;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, List<Integer> groupIdList) {
        this.name = name;
        this.groupIdList = groupIdList;
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
}
