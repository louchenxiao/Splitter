package com.example.splitter.service;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.repository.GroupRepoImpl;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GroupServiceTest {

    @Test
    @DisplayName("ob rechnunglist richtig")
    void test_1() {
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);

        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");

        Gruppe group1 = groupService.create("goroup1",List.of(a,b,c));
        Rechnung r1 = new Rechnung("Hotelzimmer", Money.of(564, "EUR"), a, List.of(a,b,c));
        Rechnung r2 = new Rechnung("Theatervorstellung", Money.of(200, "EUR"), a, List.of(a,b));


        groupService.findByGroupId(group1.getId()).getRechnungList().add(r1);
        groupService.findByGroupId(group1.getId()).getRechnungList().add(r2);

        assertThat(group1.getRechnungList()).isEqualTo(List.of(r1,r2));
    }

    @Test
    @DisplayName("ob group off")
    void test_2() {
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);

        Person a = new Person("a");
        Person b = new Person("b");

        Gruppe group1 = groupService.create("goroup1",List.of(a,b));

        Rechnung r1 = new Rechnung("Theatervorstellung", Money.of(200, "EUR"), a, List.of(a,b));


        groupService.findByGroupId(group1.getId()).getRechnungList().add(r1);

        //?????????
        assertThat(groupService.findByGroupId(group1.getId()).getGeschlossen()).isEqualTo(false);
    }

    @Test
    @DisplayName("get all gouplist from idlist")
    void test_3() {
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Gruppe group1 = groupService.create("goroup1",List.of(a,b,d,e));
        Gruppe group2 = groupService.create("goroup2",List.of(d,e,f));
        Gruppe group3 = groupService.create("goroup2",List.of(b,c,f));
        List<Gruppe> gruppeList = groupService.getAllGruppe(List.of(group1.getId(),group3.getId()));
        assertThat(gruppeList.get(0)).isEqualTo(group1);
        assertThat(gruppeList.get(1)).isEqualTo(group3);

    }

}
