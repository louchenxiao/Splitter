package com.example.splitter.repository;

import com.example.splitter.domain.Person;
import com.example.splitter.service.PersonRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepoImpl implements PersonRepo {
    private final List<Person> personList =new ArrayList<>();

    @Override
    public List<Person> findAll(){
        return personList;
    }
    @Override
    public Person findByName(String name){
        return personList.stream().filter(e->e.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public void save(Person person){
        personList.add(person);
    }


}
