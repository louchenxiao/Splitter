package com.example.splitter.service;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.repository.GroupRepoImpl;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GroupServiceTest {

    @Test
    @DisplayName("not find group")
    void test_s1(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);

        Gruppe byGroupId = groupService.findByGroupId(1);
        assertThat(byGroupId).isEqualTo(null);
    }

    @Test
    @DisplayName("find group")
    void test_s2(){
        GroupRepo groupRepo = new GroupRepoImpl();
        Gruppe gruppe = new Gruppe(1,"a",List.of());
        groupRepo.save(gruppe);
        GroupService groupService = new GroupService(groupRepo);

        Gruppe byGroupId = groupService.findByGroupId(1);
        assertThat(byGroupId).isEqualTo(gruppe);
    }

    @Test
    @DisplayName("group not exist")
    void test_exist_1(){
        GroupRepo groupRepo = new GroupRepoImpl();
        Gruppe gruppe = new Gruppe(1,"a",List.of());
        groupRepo.save(gruppe);
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.exist(2)).isEqualTo(false);
    }

    @Test
    @DisplayName("group exist")
    void test_exist_2(){
        GroupRepo groupRepo = new GroupRepoImpl();
        Gruppe gruppe = new Gruppe(1,"a",List.of());
        groupRepo.save(gruppe);
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.exist(1)).isEqualTo(true);
    }

    @Test
    @DisplayName("toInteger with not number string")
    void test_toString_1(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.toInteger("s")).isEqualTo(-1);
    }

    @Test
    @DisplayName("toInteger with number string")
    void test_toString_2(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.toInteger("1")).isEqualTo(1);
    }

    @Test
    @DisplayName("check group not exist with not number string")
    void test_check_2(){
        GroupRepo groupRepo = new GroupRepoImpl();
        Gruppe gruppe = new Gruppe(1,"a",List.of());
        groupRepo.save(gruppe);
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.check("s")).isEqualTo(-1);
    }

    @Test
    @DisplayName("check group not exist with number string")
    void test_check_3(){
        GroupRepo groupRepo = new GroupRepoImpl();
        Gruppe gruppe = new Gruppe(1,"a",List.of());
        groupRepo.save(gruppe);
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.check("2")).isEqualTo(-1);
    }

    @Test
    @DisplayName("check group exist return id")
    void test_check_4(){
        GroupRepo groupRepo = new GroupRepoImpl();
        Gruppe gruppe = new Gruppe(5,"a",List.of());
        groupRepo.save(gruppe);
        GroupService groupService = new GroupService(groupRepo);

        assertThat(groupService.check("5")).isEqualTo(5);
    }

    @Test
    @DisplayName("group create")
    void test_create_1(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        groupService.create("jj",List.of());

        List<Gruppe> gruppeList = groupRepo.findAll()
                .stream().filter(e -> e.getName().equals("jj")).toList();

        assertThat(gruppeList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("add rechnung")
    void test_add_rechnung_1(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Gruppe gruppe = new Gruppe(1,"nn",List.of(a));
        groupRepo.save(gruppe);
        Rechnung rechnung = new Rechnung("gg"
                ,Money.of(99,"EUR"),a,List.of(a));
        groupService.addRechnung(1,rechnung);

        assertThat(gruppe.getRechnungList().size()).isOne();

    }

    @Test
    @DisplayName("add rechnung with wrong id")
    void test_add_rechnung_2(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Gruppe gruppe = new Gruppe(1,"nn",List.of(a));
        groupRepo.save(gruppe);
        Rechnung rechnung = new Rechnung("gg"
                ,Money.of(99,"EUR"),a,List.of(a));
        groupService.addRechnung(2,rechnung);
        assertThat(gruppe.getRechnungList().size()).isZero();

    }

    @Test
    @DisplayName("get all group")
    void test_get_all_group(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Gruppe gruppe = new Gruppe(1,"nn",List.of(a));
        groupRepo.save(gruppe);
        groupRepo.save(new Gruppe(3,"jj",List.of()));
        List<Gruppe> allGruppe = groupService.getAllGruppe(List.of(1, 2, 3, 4, 5));
        assertThat(allGruppe.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("get all group with empty id list")
    void test_get_all_group_2(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Gruppe gruppe = new Gruppe(1,"nn",List.of(a));
        groupRepo.save(gruppe);
        groupRepo.save(new Gruppe(3,"jj",List.of()));
        List<Gruppe> allGruppe = groupService.getAllGruppe(List.of());
        assertThat(allGruppe.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("close group with not exist id")
    void test_close_group_1(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Gruppe gruppe = new Gruppe(1,"nn",List.of(a));
        groupRepo.save(gruppe);
        groupRepo.save(new Gruppe(3,"jj",List.of()));
        groupService.closeGruppe(99);
        int size = groupRepo.findAll().stream().filter(e -> e.getGeschlossen().equals(true)).toList().size();
        assertThat(size).isEqualTo(0);
    }

    @Test
    @DisplayName("close group with  exist id")
    void test_close_group_2(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Gruppe gruppe = new Gruppe(1,"nn",List.of(a));
        groupRepo.save(gruppe);
        groupRepo.save(new Gruppe(3,"jj",List.of()));
        groupService.closeGruppe(1);
        int size = groupRepo.findAll().stream().filter(e -> e.getGeschlossen().equals(true)).toList().size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("when group closed cant add rechnung")
    void test_add_rechnung_close(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Gruppe gruppe = new Gruppe(1,"nn",List.of(a));
        groupRepo.save(gruppe);
        groupRepo.save(new Gruppe(3,"jj",List.of()));
        groupService.closeGruppe(1);
        Rechnung rechnung = new Rechnung("aa",Money.of(66,"EUR"),a,List.of(a));
        groupService.addRechnung(1,rechnung);

        assertThat(gruppe.getRechnungList().size()).isZero();
    }

    @Test
    @DisplayName("when group open can add rechnung")
    void test_add_rechnung_open(){
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Gruppe gruppe = new Gruppe(1,"nn",List.of(a));
        groupRepo.save(gruppe);
        groupRepo.save(new Gruppe(3,"jj",List.of()));
        Rechnung rechnung = new Rechnung("aa",Money.of(66,"EUR"),a,List.of(a));
        groupService.addRechnung(1,rechnung);

        assertThat(gruppe.getRechnungList().size()).isOne();
    }



    @Test
    @DisplayName("ob rechnung list richtig")
    void test_1() {
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);

        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");

        Gruppe group1 = groupService.create("group1",List.of(a,b,c));
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

        Gruppe group1 = groupService.create("group1",List.of(a,b));

        Rechnung r1 = new Rechnung("Theatervorstellung", Money.of(200, "EUR"), a, List.of(a,b));

        groupService.findByGroupId(group1.getId()).getRechnungList().add(r1);
        assertThat(groupService.findByGroupId(group1.getId()).getGeschlossen()).isEqualTo(false);
    }

    @Test
    @DisplayName("get all group list from id list")
    void test_3() {
        GroupRepo groupRepo = new GroupRepoImpl();
        GroupService groupService = new GroupService(groupRepo);
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Gruppe group1 = groupService.create("group1",List.of(a,b,d,e));
        Gruppe group2 = groupService.create("group2",List.of(d,e,f));
        Gruppe group3 = groupService.create("group2",List.of(b,c,f));
        List<Gruppe> gruppeList = groupService.getAllGruppe(List.of(group1.getId(),group3.getId()));
        assertThat(gruppeList.get(0)).isEqualTo(group1);
        assertThat(gruppeList.get(1)).isEqualTo(group3);

    }

}
