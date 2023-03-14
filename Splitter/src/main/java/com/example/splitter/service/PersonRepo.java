package com.example.splitter.service;

import com.example.splitter.domain.Person;

import java.util.List;

public interface PersonRepo {
    List<Person> findAll();

    Person findByName(String name);


    void save(Person person);

}
