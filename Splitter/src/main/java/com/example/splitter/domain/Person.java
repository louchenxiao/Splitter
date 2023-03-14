package com.example.splitter.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Person {
    private String name;

    private final List<Integer> groupIdList;

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


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getName(), person.getName()) && Objects.equals(getGroupIdList(), person.getGroupIdList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGroupIdList());
    }
}
