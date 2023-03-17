package com.example.splitter.domain;



import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Person {

    @Id
    private  Integer id;
    private String name;

    private final List<Integer> list;

    public Person(Integer id, String name, List<Integer> list) {
        this.id = id;
        this.name = name;
        this.list = list;
    }

    public static Person  createPerson(String name) {
        return new Person(null,name,new ArrayList<>());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getList() {
        return list;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groupIdList=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(list, person.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, list);
    }
}
