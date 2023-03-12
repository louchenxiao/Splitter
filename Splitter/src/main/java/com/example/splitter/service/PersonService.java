package com.example.splitter.service;

import com.example.splitter.domain.Person;
import com.example.splitter.repository.PersonRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PersonService {
    private PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public void addGruppeId(Integer id, Person person){person.getGroupIdList().add(id);
    }

    public Person findPerson(String name){
        return personRepo.findByName(name);
    }

    public Person creatPerson(String name) {
        if(exist(name)){
            return findPerson(name);
        }
        else {
            Person person = new Person(name);
            personRepo.save(person);
            return person;
        }
    }

    private boolean exist(String name) {
        return personRepo.findAll().stream().filter(e->e.getName().equals(name)).count()==1;
    }


    public List<Person> createPersonByList(List<String> names) {
        List<Person> personList = new ArrayList<>();
        for (String s:names) {
            Person person = creatPerson(s);
            personList.add(person);
        }
        return personList;
    }
}
