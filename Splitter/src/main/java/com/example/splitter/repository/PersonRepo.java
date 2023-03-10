package com.example.splitter.repository;

import com.example.splitter.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class PersonRepo {
    private List<Person> personList =new ArrayList<>();

    public Person findByName(String name){
        return personList.stream().filter(e->e.getName().equals(name)).findAny().orElseThrow();
    }

    public boolean exit(String name){
        return !personList.stream().filter(e->e.getName().equals(name)).toList().isEmpty();
    }

    public void save(Person person){
        personList.add(person);
    }

    public void addGruppe(Integer integer, Person person){
        person.getGroupIdList().add(integer);
    }
}
