package com.example.splitter.service;

import com.example.splitter.SplitterApplication;
import com.example.splitter.domain.Gruppe;

import com.example.splitter.domain.Rechnung;

import com.example.splitter.persistence.GroupRepoImpl;


import com.example.splitter.persistence.SpringdataGroupRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;







public class GroupServiceTest {

/*
    @Autowired
    SpringdataGroupRepo springdataGroupRepo;
    GroupRepo groupRepo;


    @BeforeEach
    void init() {
        groupRepo = new GroupRepoImpl(springdataGroupRepo);
    }*/


    @Test
    @DisplayName("not find group")
    void test_s1() {
        /*GroupService groupService = new GroupService(groupRepo);
        groupRepo.findAll();

        System.out.println(groupService.findAll());
        assertThat(groupService.findAll().size()).isEqualTo(1);*/


        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);
        when(groupRepo.findByID(1)).thenReturn(null);
        Gruppe byGroupId = groupService.findByGroupId(1);
        assertThat(byGroupId).isEqualTo(null);
    }


    @Test
    @DisplayName("find group")
    void test_s2() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        Gruppe gruppe = new Gruppe("a", List.of());
        GroupService groupService = new GroupService(groupRepo);
        when(groupRepo.findByID(1)).thenReturn(gruppe);
        Gruppe byGroupId = groupService.findByGroupId(1);
        assertThat(byGroupId).isEqualTo(gruppe);
    }

    @Test
    @DisplayName("group not exist")
    void test_exist_1() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        Gruppe gruppe = new Gruppe(1, "a", List.of(), new HashSet<>(), false);
        GroupService groupService = new GroupService(groupRepo);
        when(groupRepo.findAll()).thenReturn(List.of(gruppe));
        assertThat(groupService.exist(2)).isEqualTo(false);
    }

    @Test
    @DisplayName("group exist")
    void test_exist_2() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        Gruppe gruppe = new Gruppe(1, "a", List.of(), new HashSet<>(), false);
        groupRepo.save(gruppe);
        GroupService groupService = new GroupService(groupRepo);
        when(groupRepo.findAll()).thenReturn(List.of(gruppe));

        assertThat(groupService.exist(1)).isEqualTo(true);
    }


    @Test
    @DisplayName("toInteger with not number string")
    void test_toString_1() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.toInteger("s")).isEqualTo(-1);
    }


    @Test
    @DisplayName("toInteger with number string")
    void test_toString_2() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.toInteger("1")).isEqualTo(1);
    }


    @Test
    @DisplayName("check group not exist with not number string")
    void test_check_2() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        Gruppe gruppe = new Gruppe(1, "a", List.of(), new HashSet<>(), false);

        GroupService groupService = new GroupService(groupRepo);
        when(groupRepo.findAll()).thenReturn(List.of(gruppe));

        assertThat(groupService.check("s")).isEqualTo(-1);
    }


    @Test
    @DisplayName("check group not exist with number string")
    void test_check_3() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        Gruppe gruppe = new Gruppe(1, "a", List.of(), new HashSet<>(), false);

        GroupService groupService = new GroupService(groupRepo);
        when(groupRepo.findAll()).thenReturn(List.of(gruppe));


        assertThat(groupService.check("2")).isEqualTo(-1);
    }


    @Test
    @DisplayName("check group exist return id")
    void test_check_4() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        Gruppe gruppe = new Gruppe(5, "a", List.of(), new HashSet<>(), false);
        GroupService groupService = new GroupService(groupRepo);
        when(groupRepo.findAll()).thenReturn(List.of(gruppe));

        assertThat(groupService.check("5")).isEqualTo(5);
    }


    @Test
    @DisplayName("group create")
    void test_create_1() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);

        GroupService groupService = new GroupService(groupRepo);

        groupService.create("a", List.of("a"));


        verify(groupRepo).save(new Gruppe(null, "a", List.of("a"), new HashSet<>(), false));
    }


    @Test
    @DisplayName("add rechnung")
    void test_add_rechnung_1() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);
        Gruppe gruppe = new Gruppe(1, "a", List.of("a", "b"), new HashSet<>(), false);
        Rechnung rechnung = new Rechnung("gg"
                , new BigDecimal(10), "a", Set.of("a"));


        when(groupRepo.findAll()).thenReturn(List.of(gruppe));
        when(groupRepo.findByID(1)).thenReturn(gruppe);
        groupService.addRechnung(1, rechnung);
        assertThat(gruppe.getRechnungSet()).isEqualTo(Set.of(rechnung));


    }


    @Test
    @DisplayName("add rechnung with wrong id")
    void test_add_rechnung_2() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);
        Gruppe gruppe = new Gruppe(1, "a", List.of("a", "b"), new HashSet<>(), false);
        Rechnung rechnung = new Rechnung("gg"
                , new BigDecimal(10), "a", Set.of("a"));


        when(groupRepo.findAll()).thenReturn(List.of(gruppe));
        when(groupRepo.findByID(4)).thenReturn(null);
        groupService.addRechnung(4, rechnung);
        assertThat(gruppe.getRechnungSet()).isEqualTo(Set.of());

    }


    @Test
    @DisplayName("get all group")
    void test_get_all_group() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);
        Gruppe gruppe = new Gruppe(1, "a", List.of("a", "b"), new HashSet<>(), false);
        Gruppe gruppe2 = new Gruppe(2, "a", List.of("a", "b"), new HashSet<>(), false);

        when(groupRepo.findAll()).thenReturn(List.of(gruppe, gruppe2));
        List<Gruppe> allGruppe = groupService.getAllGruppe(List.of(1, 2, 3, 4, 5));
        assertThat(allGruppe.size()).isEqualTo(2);
    }


    @Test
    @DisplayName("get all group with empty id list")
    void test_get_all_group_2() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);
        Gruppe gruppe = new Gruppe(1, "a", List.of("a", "b"), new HashSet<>(), false);
        Gruppe gruppe2 = new Gruppe(2, "a", List.of("a", "b"), new HashSet<>(), false);

        when(groupRepo.findAll()).thenReturn(List.of(gruppe, gruppe2));
        List<Gruppe> allGruppe = groupService.getAllGruppe(List.of());
        assertThat(allGruppe.size()).isEqualTo(0);
    }


    @Test
    @DisplayName("close group with not exist id")
    void test_close_group_1() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);
        Gruppe gruppe = new Gruppe(1, "a", List.of("a", "b"), new HashSet<>(), false);
        Gruppe gruppe2 = new Gruppe(2, "a", List.of("a", "b"), new HashSet<>(), false);

        when(groupRepo.findAll()).thenReturn(List.of(gruppe, gruppe2));
        groupService.closeGruppe(99);
        int size = groupRepo.findAll().stream().filter(e -> e.getGeschlossen().equals(true)).toList().size();
        assertThat(size).isEqualTo(0);
    }

    @Test
    @DisplayName("close group with exist id")
    void test_close_group_2() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);
        Gruppe gruppe = new Gruppe(1, "a", List.of("a", "b"), new HashSet<>(), false);
        Gruppe gruppe2 = new Gruppe(2, "a", List.of("a", "b"), new HashSet<>(), false);

        when(groupRepo.findAll()).thenReturn(List.of(gruppe, gruppe2));
        when(groupRepo.findByID(1)).thenReturn(gruppe);

        groupService.closeGruppe(1);
        assertThat(gruppe.getGeschlossen()).isTrue();
        verify(groupRepo).save(gruppe);

    }


    @Test
    @DisplayName("when group closed cant add rechnung")
    void test_add_rechnung_close() {
        GroupRepo groupRepo = mock(GroupRepoImpl.class);
        GroupService groupService = new GroupService(groupRepo);
        Gruppe gruppe = new Gruppe(1, "a", List.of("a", "b"), new HashSet<>(), true);
        Gruppe gruppe2 = new Gruppe(2, "a", List.of("a", "b"), new HashSet<>(), false);

        when(groupRepo.findAll()).thenReturn(List.of(gruppe, gruppe2));
        when(groupRepo.findByID(1)).thenReturn(gruppe);


        Rechnung rechnung = new Rechnung("gg"
                , new BigDecimal(10), "a", Set.of("a"));

        groupService.addRechnung(1, rechnung);


        assertThat(gruppe.getRechnungSet().size()).isZero();
    }
}





