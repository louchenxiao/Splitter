package com.example.splitter.service;

import com.example.splitter.domain.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private final PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public void addGruppeId(Integer id, Person person){
        if(exist(person.getName())) {
            person.getList().add(id);
            personRepo.save(person);
        }
    }
    public Person findPerson(String name){
        return personRepo.findByName(name);
    }

    public Person creatPerson(String name) {
        if(exist(name)){
            return findPerson(name);
        }
         else {
            Person person = Person.createPerson(name);
            personRepo.save(person);
            return person;
        }
    }



    public boolean exist(String name) {
        return personRepo.findAll().stream().filter(e->e.getName().equals(name)).count()==1;
    }


    public List<Person> createPersonByList(List<String> names) {
        List<String> strings = names.stream().distinct().toList();
        List<Person> personList = new ArrayList<>();
        for (String s:strings) {
            Person person = creatPerson(s);
            personList.add(person);
        }
        return personList;
    }

    public List<Person> findAll(){
        return personRepo.findAll();
    }
}
