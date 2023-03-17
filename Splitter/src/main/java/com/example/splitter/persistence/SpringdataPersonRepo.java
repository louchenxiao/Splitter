package com.example.splitter.persistence;

import com.example.splitter.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringdataPersonRepo extends CrudRepository<Person,String> {
    List<Person> findAll();


}
