package com.example.splitter.persistence;

import com.example.splitter.domain.Person;
import com.example.splitter.service.PersonRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepoImpl implements PersonRepo {
    private final SpringdataPersonRepo springdataPersonRepo;

    public PersonRepoImpl(SpringdataPersonRepo springdataPersonRepo) {
        this.springdataPersonRepo = springdataPersonRepo;
    }

    @Override
    public List<Person> findAll(){
        return springdataPersonRepo.findAll();
    }
    @Override
    public Person findByName(String name){
        return springdataPersonRepo.findAll().stream().filter(e->e.getName().equals(name)).
                findAny().orElse(null);
    }

    @Override
    public void save(Person person){
        springdataPersonRepo.save(person);
    }


}
