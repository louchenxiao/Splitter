package com.example.splitter.service;

import com.example.splitter.domain.Person;
import com.example.splitter.persistence.PersonRepoImpl;
import com.example.splitter.persistence.SpringdataPersonRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJdbcTest
public class PersonServiceJdbcTest {
     @Autowired
     SpringdataPersonRepo springdataPersonRepo;
     PersonRepo personRepo;

    @BeforeEach
    void init() {
        personRepo = new PersonRepoImpl(springdataPersonRepo);
    }


    @Test
    @DisplayName("findAll successfully")
    void test_9(){
        PersonService personService =new PersonService(personRepo);
        personService.creatPerson("1");
        personService.creatPerson("2");
        assertThat(personService.findAll().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("create person successful")
    void test_1(){
        PersonService personService =new PersonService(personRepo);
        personService.creatPerson("ok");
        assertThat(personService.findPerson("ok").getName()).isEqualTo("ok");
    }

    @Test
    @DisplayName("create person successful,but cant find person")
    void test_2(){
        PersonService personService =new PersonService(personRepo);
        personService.creatPerson("ok");
        assertThat(personService.findPerson("bad")).isEqualTo(null);
    }

    @Test
    @DisplayName("if person exist?")
    void test_3(){
        PersonService personService =new PersonService(personRepo);
        personService.creatPerson("ok");
        assertThat(personService.exist("ok")).isTrue();
    }

    @Test
    @DisplayName("if person not exist?")
    void test_4(){
        PersonService personService =new PersonService(personRepo);
        personService.creatPerson("ok");
        assertThat(personService.exist("bad")).isFalse();
    }

    @Test
    @DisplayName("add gruppe successfully")
    void test_5(){
        PersonService personService =new PersonService(personRepo);
        personService.creatPerson("ok");
        personService.addGruppeId(1,personService.findPerson("ok"));
        Person ok = personService.findPerson("ok");
        assertThat(ok.getList()).contains(1);

    }

    @Test
    @DisplayName("createPersonByIdList successfully")
    void test_6(){
        PersonService personService =new PersonService(personRepo);
        personService.createPersonByList(List.of("1","2"));
        assertThat(personService.findAll().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("createPersonByIdList successfully with some person already in Repo")
    void test_7(){
        PersonService personService =new PersonService(personRepo);
        personService.creatPerson("1");
        personService.createPersonByList(List.of("1","2"));
        assertThat(personService.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("createPersonByIdList successfully with some person not in Repo")
    void test_8(){
        PersonService personService =new PersonService(personRepo);
        personService.creatPerson("3");
        personService.createPersonByList(List.of("1","2"));
        assertThat(personService.findAll().size()).isEqualTo(3);
    }



}
