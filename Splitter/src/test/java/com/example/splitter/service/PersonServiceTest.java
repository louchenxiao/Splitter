package com.example.splitter.service;

import com.example.splitter.domain.Person;
import com.example.splitter.persistence.GroupRepoImpl;
import com.example.splitter.persistence.PersonRepoImpl;
import com.example.splitter.persistence.SpringdataPersonRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class PersonServiceTest {


    /*@Autowired
    SpringdataPersonRepo springdataPersonRepo;
    PersonRepo personRepo;

    @BeforeEach
    void init() {
        personRepo = new PersonRepoImpl(springdataPersonRepo);
    }*/
    @Test
    @DisplayName("find person in personRepo")
    void test_1() {


       /* PersonService personService =new PersonService(personRepo);
        personRepo.findAll();*/


        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);

        Person a = new Person(1, "2", new ArrayList<>());
        when(personRepo.findByName("2")).thenReturn(a);

        assertThat(personService.findPerson("2")).isEqualTo(a);
    }


    @Test
    @DisplayName("find person not in personRepo")
    void test_not_in() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "2", new ArrayList<>());
        when(personRepo.findByName("2")).thenReturn(a);
        assertThat(personService.findPerson("b")).isEqualTo(null);
    }


    @Test
    @DisplayName("person exist")
    void test_exist() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "2", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a));
        assertThat(personService.exist("2")).isEqualTo(true);
    }


    @Test
    @DisplayName("person not exist")
    void test_not_exist() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "2", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a));

        assertThat(personService.exist("c")).isEqualTo(false);
    }


    @Test
    @DisplayName("create person with all not in repo")
    void test_2() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);

        when(personRepo.findAll()).thenReturn(List.of());
        personService.creatPerson("a");
        verify(personRepo).save(new Person(null,"a",List.of()));
    }




    @Test
    @DisplayName("create person with all in repo")
    void test_3() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        when(personRepo.findByName("a")).thenReturn(a);
        when(personRepo.findByName("b")).thenReturn(b);

        personService.creatPerson("a");
        personService.creatPerson("b");
        verify(personRepo,times(0)).save(any());
    }

    @Test
    @DisplayName("create person with one in repo and one not")
    void test_4() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(b));
        when(personRepo.findByName("a")).thenReturn(a);
        when(personRepo.findByName("b")).thenReturn(null);

        personService.creatPerson("a");
        personService.creatPerson("b");
        verify(personRepo,times(1)).save(any());
    }

    @Test
    @DisplayName("add group when person in repo")
    void test_5() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        personService.addGruppeId(1,a);
        verify(personRepo,times(1)).save(any());
    }

    @Test
    @DisplayName("fail add group when person not in repo")
    void test_6() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        Person c = new Person(3, "c", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        personService.addGruppeId(1,c);
        verify(personRepo,times(0)).save(c);
    }

    @Test
    @DisplayName("create person by empty list")
    void test_7() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        personService.createPersonByList(List.of());
        verify(personRepo,times(0)).save(any());
    }

    @Test
    @DisplayName("create person by list, all name in repo")
    void test_8() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        personService.createPersonByList(List.of("a","b"));
        verify(personRepo,times(0)).save(any());
    }

    @Test
    @DisplayName("create person by list, all name not in repo")
    void test_9() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        personService.createPersonByList(List.of("c","d"));
        verify(personRepo,times(2)).save(any());
    }

    @Test
    @DisplayName("create person by list, two name not in repo, two in")
    void test_10() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        personService.createPersonByList(List.of("c","d","a","b"));
        verify(personRepo,times(2)).save(any());
    }

    @Test
    @DisplayName("create person by list, two same name")
    void test_11() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        personService.createPersonByList(List.of("c","c"));
        verify(personRepo,times(1)).save(any());
    }


    @Test
    @DisplayName("create person by list, two different name and not in repo")
    void test_12() {
        PersonRepo personRepo = mock(PersonRepoImpl.class);
        PersonService personService = new PersonService(personRepo);
        Person a = new Person(1, "a", new ArrayList<>());
        Person b = new Person(2, "b", new ArrayList<>());
        when(personRepo.findAll()).thenReturn(List.of(a,b));
        personService.createPersonByList(List.of("d","c"));
        verify(personRepo,times(2)).save(any());
    }










}


