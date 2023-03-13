package com.example.splitter.service;

import com.example.splitter.domain.Person;
import com.example.splitter.repository.PersonRepoImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PersonServiceTest {

    @Test
    @DisplayName("ob person in personList add")
    void test_1() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        Person person1 = personService.creatPerson("a");
        assertThat(personService.findPerson("a")).isEqualTo(person1);

    }

    @Test
    @DisplayName("ob gleiche person in personList add")
    void test_2() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personService.creatPerson("a");
        personService.creatPerson("a");
        assertThat(personRepo.findAll().size()).isEqualTo(1);

    }

    @Test
    @DisplayName("get personlist from stringlist")
    void test_3() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personService.creatPerson("a");
        personService.creatPerson("b");
        personService.creatPerson("c");
        personService.creatPerson("d");
        List<String> stringList = List.of("a","b","c");
        List<Person> personList = personService.createPersonByList(stringList);
        assertThat(personList.get(0)).isEqualTo(personService.findPerson("a"));
        assertThat(personList.get(1)).isEqualTo(personService.findPerson("b"));
        assertThat(personList.get(2)).isEqualTo(personService.findPerson("c"));

    }



}
