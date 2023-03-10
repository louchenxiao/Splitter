package com.example.splitter.service;
import com.example.splitter.domain.PersonalBill;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.domain.Result;
import com.example.splitter.repository.GroupRepo;
import com.example.splitter.repository.PersonRepo;
import com.example.splitter.repository.RechnungRepo;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


class UeberweisungTest {
/*
    @Test
    void test_1() {
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");

        Rechnung r1 = new Rechnung("1", Money.of(50, "EUR"), a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(50, "EUR"), a, List.of(a, c));

        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);


        u.addPerson(1, a, LocalDateTime.now());
        u.addPerson(1, b, LocalDateTime.now());
        u.addPerson(1, c, LocalDateTime.now());

        u.addRechnung(1, r1, LocalDateTime.now());
        u.addRechnung(1, r2, LocalDateTime.now());

        List<Person> personList = groupRepo.findByID(1).orElseThrow().getPersonList();
        List<Rechnung> rechnungList = groupRepo.findByID(1).orElseThrow().getRechnungList();


        assertThat(u.rechnen(personList, rechnungList)).isEqualTo(List.of(new PersonalBill(a, Money.of(50, "EUR")),
                new PersonalBill(b, Money.of(-25, "EUR")), new PersonalBill(c, Money.of(-25, "EUR"))));
    }

    @Test
    @DisplayName("Szenario 1: Summieren von Auslagen")
    void test_s1() {
        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1", Money.of(10, "EUR"), a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(20, "EUR"), a, List.of(a, b));

        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);


        u.addPerson(1, a, LocalDateTime.now());
        u.addPerson(1, b, LocalDateTime.now());

        u.addRechnung(1, r1, LocalDateTime.now());
        u.addRechnung(1, r2, LocalDateTime.now());

        List<Result> results = u.result(1);

        assertThat(results).isEqualTo(List.of(new Result(a, b, Money.of(15, "EUR"))));


    }

    @Test
    @DisplayName("Szenario 2: Ausgleich")
    void test_s2() {
        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1", Money.of(10, "EUR"), a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(20, "EUR"), b, List.of(a, b));

        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);

        u.addPerson(1, a, LocalDateTime.now());
        u.addPerson(1, b, LocalDateTime.now());

        u.addRechnung(1, r1, LocalDateTime.now());
        u.addRechnung(1, r2, LocalDateTime.now());



        List<Result> results = u.result(1);

        assertThat(results).isEqualTo(List.of(new Result(b, a, Money.of(5, "EUR"))));
    }

    @Test
    @DisplayName("Szenario 3: Zahlung ohne eigene Beteiligung")
    void test_s3() {
        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1", Money.of(10, "EUR"), a, List.of(b));
        Rechnung r2 = new Rechnung("2", Money.of(20, "EUR"), a, List.of(a, b));

        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);

        u.addPerson(1, a, LocalDateTime.now());
        u.addPerson(1, b, LocalDateTime.now());

        u.addRechnung(1, r1, LocalDateTime.now());
        u.addRechnung(1, r2, LocalDateTime.now());


        List<Result> results = u.result(1);

        assertThat(results).isEqualTo(List.of(new Result(a, b, Money.of(20, "EUR"))));
    }

    @Test
    @DisplayName("Szenario 4: Ringausgleich")
    void test_s4() {
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Rechnung r1 = new Rechnung("1", Money.of(10, "EUR"), a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(10, "EUR"), b, List.of(c, b));
        Rechnung r3 = new Rechnung("3", Money.of(10, "EUR"), c, List.of(a, c));

        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);

        u.addPerson(1, a, LocalDateTime.now());
        u.addPerson(1, b, LocalDateTime.now());
        u.addPerson(1, c, LocalDateTime.now());

        u.addRechnung(1, r1, LocalDateTime.now());
        u.addRechnung(1, r2, LocalDateTime.now());
        u.addRechnung(1, r3, LocalDateTime.now());


        List<Result> results = u.result(1);

        assertThat(results).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Szenario 5: ABC Beispiel aus der Einführung")
    void test_s5() {
        Person a = new Person("Anton");
        Person b = new Person("Berta");
        Person c = new Person("Christian");
        Rechnung r1 = new Rechnung("1", Money.of(60, "EUR"), a, List.of(a, b, c));
        Rechnung r2 = new Rechnung("2", Money.of(30, "EUR"), b, List.of(a, c, b));
        Rechnung r3 = new Rechnung("3", Money.of(100, "EUR"), c, List.of(b, c));

        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);

        u.addPerson(1, a, LocalDateTime.now());
        u.addPerson(1, b, LocalDateTime.now());
        u.addPerson(1, c, LocalDateTime.now());

        u.addRechnung(1, r1, LocalDateTime.now());
        u.addRechnung(1, r2, LocalDateTime.now());
        u.addRechnung(1, r3, LocalDateTime.now());


        List<Result> results = u.result(1);

        assertThat(results).isEqualTo(List.of(new Result(a, b, Money.of(30, "EUR")), new Result(c, b, Money.of(20, "EUR"))));
    }

    @Test
    @DisplayName("Szenario 6: Beispiel aus der Aufgabenstellung")
    void test_s6() {
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Rechnung r1 = new Rechnung("Hotelzimmer", Money.of(564, "EUR"), a, List.of(a, b, c, d, e, f));
        Rechnung r2 = new Rechnung("Benzin (Hinweg)", Money.of(38.58, "EUR"), b, List.of(a, b));
        Rechnung r3 = new Rechnung("Benzin (Rückweg)", Money.of(38.58, "EUR"), b, List.of(a, b, d));
        Rechnung r4 = new Rechnung("Benzin", Money.of(82.11, "EUR"), c, List.of(c, e, f));
        Rechnung r5 = new Rechnung("Städtetour", Money.of(96, "EUR"), d, List.of(a, b, c, d, e, f));
        Rechnung r6 = new Rechnung("Theatervorstellung", Money.of(95.37, "EUR"), f, List.of(b, e, f));


        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);

        u.addPerson(1, a, LocalDateTime.now());
        u.addPerson(1, b, LocalDateTime.now());
        u.addPerson(1, c, LocalDateTime.now());
        u.addPerson(1, d, LocalDateTime.now());
        u.addPerson(1, e, LocalDateTime.now());
        u.addPerson(1, f, LocalDateTime.now());

        u.addRechnung(1, r1, LocalDateTime.now());
        u.addRechnung(1, r2, LocalDateTime.now());
        u.addRechnung(1, r3, LocalDateTime.now());
        u.addRechnung(1, r4, LocalDateTime.now());
        u.addRechnung(1, r5, LocalDateTime.now());
        u.addRechnung(1, r6, LocalDateTime.now());



        List<Result> results = u.result(1);

        assertThat(results).isEqualTo(List.of(
                new Result(a, b, Money.of(96.78, "EUR")),
                new Result(a, c, Money.of(55.26, "EUR")),
                new Result(a, d, Money.of(26.86, "EUR")),
                new Result(a, e, Money.of(169.16, "EUR")),
                new Result(a, f, Money.of(73.79, "EUR"))));
    }

    @Test
    @DisplayName("Szenario 7: Minimierung")
    @Disabled
    void test_s7() {
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Person g = new Person("g");

        Rechnung r1 = new Rechnung("1", Money.of(20, "EUR"), d, List.of(d, f));
        Rechnung r2 = new Rechnung("2", Money.of(10, "EUR"), g, List.of(b));
        Rechnung r3 = new Rechnung("3", Money.of(75, "EUR"), e, List.of(a, c, e));
        Rechnung r4 = new Rechnung("4", Money.of(50, "EUR"), f, List.of(a, f));
        Rechnung r5 = new Rechnung("5", Money.of(40, "EUR"), e, List.of(d));
        Rechnung r6 = new Rechnung("6", Money.of(40, "EUR"), f, List.of(b, f));
        Rechnung r7 = new Rechnung("7", Money.of(5, "EUR"), f, List.of(c));
        Rechnung r8 = new Rechnung("8", Money.of(30, "EUR"), g, List.of(a));


        GroupRepo u1 = new GroupRepo();

        Ueberweisung u = new Ueberweisung(u1);
        u.createGroup(1, LocalDateTime.now(),a);

        u.addPerson(1, a, LocalDateTime.now());
        u.addPerson(1, b, LocalDateTime.now());
        u.addPerson(1, c, LocalDateTime.now());
        u.addPerson(1, d, LocalDateTime.now());
        u.addPerson(1, e, LocalDateTime.now());
        u.addPerson(1, f, LocalDateTime.now());
        u.addPerson(1, g, LocalDateTime.now());

        u.addRechnung(1, r1, LocalDateTime.now());
        u.addRechnung(1, r2, LocalDateTime.now());
        u.addRechnung(1, r3, LocalDateTime.now());
        u.addRechnung(1, r4, LocalDateTime.now());
        u.addRechnung(1, r5, LocalDateTime.now());
        u.addRechnung(1, r6, LocalDateTime.now());
        u.addRechnung(1, r7, LocalDateTime.now());
        u.addRechnung(1, r8, LocalDateTime.now());


        List<Result> results = u.result(1);
        System.out.println(results);

        assertThat(results).isEqualTo(List.of());
    }

    @Test
    @DisplayName("cant add people after adding first rechnung")
    void test_s8() {
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Person h = new Person("h");
        Rechnung r1 = new Rechnung("Hotelzimmer", Money.of(564, "EUR"), a, List.of(a, b, c, d, e, f));


        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);

        u.addPerson(1, a,LocalDateTime.now());
        u.addPerson(1, b,LocalDateTime.now());
        u.addPerson(1, c,LocalDateTime.now());
        u.addPerson(1, d,LocalDateTime.now());
        u.addPerson(1, e,LocalDateTime.now());
        groupRepo.addPerson(1, f);

        groupRepo.addRechnung(1, r1);


        groupRepo.addPerson(1, h);


        assertThat(groupRepo.findByID(1).orElseThrow().getPersonList()).isEqualTo(List.of(a, b, c, d, e, f));

    }

    @Test
    @DisplayName("Dieselbe Person kann nicht wiederholt hinzugefügt werden")
    void test_s9() {
        Person a = new Person("a");

        Rechnung r1 = new Rechnung("Hotelzimmer", Money.of(564, "EUR"), a, List.of(a));



        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);
        u.addPerson(1, a,LocalDateTime.now());
        u.addPerson(1, a,LocalDateTime.now());
        u.addPerson(1, a,LocalDateTime.now());

        groupRepo.addRechnung(1, r1);

        assertThat(groupRepo.findByID(1).orElseThrow().getPersonList()).isEqualTo(List.of(a));


    }
    @Test
    @DisplayName("Jeder kann die Gruppe schließen")
    void test_s10() {
        Person a = new Person("a");

        Rechnung r1 = new Rechnung("Hotelzimmer", Money.of(564, "EUR"), a, List.of(a));

        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.now(),a);

        u.addPerson(1, a,LocalDateTime.now());

        u.addRechnung(1,r1,LocalDateTime.now());

        groupRepo.close(1);

        groupRepo.addRechnung(1, r1);

        assertThat(groupRepo.findByID(1).orElseThrow().getRechnungList()).isEqualTo(List.of(r1));


    }

    @Test
    @DisplayName("Gruppen können zu bestimmten Zeiten geschlossen werden")
    void test_s11() {
        Person a = new Person("a");

        Rechnung r1 = new Rechnung("Hotelzimmer", Money.of(564, "EUR"), a, List.of(a));

        GroupRepo groupRepo = new GroupRepo();

        Ueberweisung u = new Ueberweisung(groupRepo);
        u.createGroup(1, LocalDateTime.of(2023,2,3,11,0,0),a);

        u.addPerson(1, a,LocalDateTime.of(2023,3,3,11,0,0));

        u.addRechnung(1, r1,LocalDateTime.of(2023,3,3,11,0,0));



        groupRepo.addRechnung(1, r1);

        assertThat(groupRepo.findByID(1).orElseThrow().getRechnungList()).isEqualTo(List.of());


    }
*/


    @Test
    @DisplayName("Money with 2 person")
    void test_s11() {
        Person a = new Person("1");
        Person b = new Person("2");
        List<Person> personList = List.of(a,b);
        GroupRepo groupRepo =new GroupRepo();
        PersonRepo personRepo =new PersonRepo();
        RechnungRepo rechnungRepo =new RechnungRepo();
        Ueberweisung ueberweisung = new Ueberweisung(groupRepo,personRepo,rechnungRepo);
        List<PersonalBill> money = ueberweisung.money(personList);

        assertThat(money).isEqualTo(List.of(new PersonalBill(a,Money.of(0.00,"EUR")),
                new PersonalBill(b,Money.of(0.00,"EUR"))));


    }

}












