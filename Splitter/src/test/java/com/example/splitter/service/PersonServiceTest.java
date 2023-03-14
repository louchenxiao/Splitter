package com.example.splitter.service;

import com.example.splitter.domain.Person;
import com.example.splitter.repository.PersonRepoImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PersonServiceTest {
    @Test
    @DisplayName("find person in personRepo")
    void test_1() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        Person a = new Person("a");
        personRepo.save(a);
        assertThat(personService.findPerson("a")).isEqualTo(a);
    }

    @Test
    @DisplayName("find person not in personRepo")
    void test_not_in() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        Person a = new Person("a");
        personRepo.save(a);
        assertThat(personService.findPerson("b")).isEqualTo(null);
    }



    @Test
    @DisplayName("person exist")
    void test_exist() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personRepo.save(new Person("a"));

        assertThat(personService.exist("a")).isEqualTo(true);
    }

    @Test
    @DisplayName("person not exist")
    void test_not_exist() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personRepo.save(new Person("a"));

        assertThat(personService.exist("c")).isEqualTo(false);
    }


    @Test
    @DisplayName("create person with all not in repo")
    void test_2() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personService.creatPerson("a");
        personService.creatPerson("b");
        assertThat(personRepo.findAll()).isEqualTo(List.of(new Person("a"),new Person("b")));
    }

    @Test
    @DisplayName("create person with all  in repo")
    void test_3() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personRepo.save(new Person("a"));
        personRepo.save(new Person("b"));
        personService.creatPerson("a");
        personService.creatPerson("b");
        assertThat(personRepo.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("create person with one in repo and one not")
    void test_4() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personRepo.save(new Person("a"));
        personService.creatPerson("a");
        personService.creatPerson("b");
        assertThat(personRepo.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("add group when person in repo")
    void test_5() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        Person person = new Person("a");
        personRepo.save(person);
        personService.addGruppeId(1,person);
        assertThat(person.getGroupIdList()).isEqualTo(List.of(1));
    }

    @Test
    @DisplayName("add group when person not  in repo")
    void test_6() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        Person person = new Person("a");
        personRepo.save(person);
        personService.addGruppeId(1,new Person("f"));
        assertThat(person.getGroupIdList()).isEqualTo(List.of());
    }

    @Test
    @DisplayName("create person by empty list")
    void test_7() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personService.createPersonByList(List.of());
        assertThat(personRepo.findAll().size()).isZero();
    }

    @Test
    @DisplayName("create person by list, all name in repo")
    void test_8() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personRepo.save(new Person("a"));
        personRepo.save(new Person("b"));
        personRepo.save(new Person("c"));
        personRepo.save(new Person("d"));
        personService.createPersonByList(List.of("a","b","c","d"));
        assertThat(personRepo.findAll()).isEqualTo(List.of(new Person("a"),
                new Person("b"),new Person("c"),new Person("d")));
    }

    @Test
    @DisplayName("create person by list, all name not in repo")
    void test_9() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personService.createPersonByList(List.of("a","b","c","d"));
        assertThat(personRepo.findAll()).isEqualTo(List.of(new Person("a"),
                new Person("b"),new Person("c"),new Person("d")));
    }

    @Test
    @DisplayName("create person by list, two name not in repo, two in")
    void test_10() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personRepo.save(new Person("a"));
        personRepo.save(new Person("b"));
        personService.createPersonByList(List.of("a","b","c","d"));
        assertThat(personRepo.findAll()).isEqualTo(List.of(new Person("a"),
                new Person("b"),new Person("c"),new Person("d")));
    }

    @Test
    @DisplayName("create person by list, two same name")
    void test_11() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        personRepo.save(new Person("a"));
        List<Person> personByList = personService.createPersonByList(List.of("a", "a", "a", "a"));
        assertThat(personByList).isEqualTo(List.of(new Person("a")));
    }

    @Test
    @DisplayName("create person by list, two different name")
    void test_12() {
        PersonRepo personRepo = new PersonRepoImpl();
        PersonService personService = new PersonService(personRepo);
        List<Person> personByList = personService.createPersonByList(List.of("a", "b"));
        assertThat(personByList).isEqualTo(List.of(new Person("a"),new Person("b")));
    }










}
