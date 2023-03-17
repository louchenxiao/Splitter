package com.example.splitter.service;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.persistence.GroupRepoImpl;
import com.example.splitter.persistence.SpringdataGroupRepo;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJdbcTest
public class GroupServiceJdbcTest {


    @Autowired
    SpringdataGroupRepo springdataGroupRepo;
    GroupRepo groupRepo;



    @Test
    @DisplayName("create group successfully")
    void test_1() {
        groupRepo = new GroupRepoImpl(springdataGroupRepo);
        GroupService groupService = new GroupService(groupRepo);
        groupService.create("ok", List.of());
        groupService.create("alsoOk", List.of());
        assertThat(groupService.findAll().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("findGroupById successfully")
    void test_2() {
        groupRepo = new GroupRepoImpl(springdataGroupRepo);
        GroupService groupService = new GroupService(groupRepo);
        groupService.create("ok", List.of("2"));
        Integer id = groupService.findAll().stream().max(Comparator.comparing(Gruppe::getId)).get().getId();

        assertThat(groupService.findByGroupId(id).getName()).isEqualTo("ok");

    }

    @Test
    @DisplayName("toInteger method is right")
    void test_3() {
        groupRepo = new GroupRepoImpl(springdataGroupRepo);
        GroupService groupService = new GroupService(groupRepo);
        assertThat(groupService.toInteger("s")).isEqualTo(-1);
        assertThat(groupService.toInteger("2")).isEqualTo(2);
    }

    @Test
    @DisplayName("getAllGroup successfully")
    void test_4() {
        groupRepo = new GroupRepoImpl(springdataGroupRepo);
        GroupService groupService = new GroupService(groupRepo);
        groupService.create("ok", List.of());
        groupService.create("alsoOk", List.of());
        Integer id = groupService.findAll().stream().max(Comparator.comparing(Gruppe::getId)).get().getId();

        assertThat( groupService.getAllGruppe(List.of(id,id-1)).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("closeGroup successfully")
    void test_5() {
        groupRepo = new GroupRepoImpl(springdataGroupRepo);
        GroupService groupService = new GroupService(groupRepo);
        groupService.create("ok", List.of());
        Integer id = groupService.findAll().stream().max(Comparator.comparing(Gruppe::getId)).get().getId();
        groupService.closeGruppe(id);
        assertThat( groupService.findByGroupId(id).getGeschlossen()).isTrue();
    }

    @Test
    @DisplayName("closeGroup with not exist id")
    void test_6() {
        groupRepo = new GroupRepoImpl(springdataGroupRepo);
        GroupService groupService = new GroupService(groupRepo);
        groupService.create("ok", List.of());
        Integer id = groupService.findAll().stream().max(Comparator.comparing(Gruppe::getId)).get().getId();
        groupService.closeGruppe(id+1);
        assertThat( groupService.findByGroupId(id).getGeschlossen()).isFalse();
    }

    @Test
    @DisplayName("getAllGroup with some id not in database")
    void test_7() {
        groupRepo = new GroupRepoImpl(springdataGroupRepo);
        GroupService groupService = new GroupService(groupRepo);
        groupService.create("ok", List.of());
        groupService.create("alsoOk", List.of());
        Integer id = groupService.findAll().stream().max(Comparator.comparing(Gruppe::getId)).get().getId();
        assertThat( groupService.getAllGruppe(List.of(id,id-1,id+1,id+2)).size()).isEqualTo(2);
    }








}