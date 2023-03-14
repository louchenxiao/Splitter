package com.example.splitter.service;
import com.example.splitter.domain.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


class SplitterServiceTest {
    @Test
    @DisplayName("init with not empty list")
    void test_init_1(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        List<Person> personList = List.of(a,b,c);
        SplitterService splitterService = new SplitterService();
        List<PersonalBill> init = splitterService.init(personList);
        assertThat(init).isEqualTo(List.of(new PersonalBill(a,Money.of(0,"EUR"))
                ,new PersonalBill(b,Money.of(0,"EUR"))
                ,new PersonalBill(c,Money.of(0,"EUR"))));
    }

    @Test
    @DisplayName("init with empty list")
    void test_init_2(){
        List<Person> personList = List.of();
        SplitterService splitterService = new SplitterService();
        List<PersonalBill> init = splitterService.init(personList);
        assertThat(init).isEqualTo(List.of());
    }

    @Test
    @DisplayName("rechnen with empty list")
    void test_rechnen_personal_bill_1(){
        List<Person> personList = List.of();
        List<Rechnung> rechnungList = List.of();
        SplitterService splitterService = new SplitterService();

        List<PersonalBill> rechnen = splitterService.rechnenBill(personList, rechnungList);

        assertThat(rechnen).isEqualTo(List.of());
    }

    @Test
    @DisplayName("rechnen with empty rechnung list")
    void test_rechnen_personal_bill_2(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        List<Person> personList = List.of(a,b,c);
        List<Rechnung> rechnungList = List.of();
        SplitterService splitterService = new SplitterService();

        List<PersonalBill> rechnen = splitterService.rechnenBill(personList, rechnungList);

        assertThat(rechnen).isEqualTo(List.of(new PersonalBill(a, Money.of(0,"EUR"))
                ,new PersonalBill(b, Money.of(0,"EUR"))
                ,new PersonalBill(c, Money.of(0,"EUR"))));
    }

    @Test
    @DisplayName("rechnen with empty person list")
    void test_rechnen_personal_bill_3(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Rechnung r1 = new Rechnung("1", Money.of(50, "EUR"),a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(50, "EUR"),a, List.of(a, c));
        List<Person> personList = List.of();
        List<Rechnung> rechnungList = List.of(r1,r2);
        SplitterService splitterService = new SplitterService();

        List<PersonalBill> rechnen = splitterService.rechnenBill(personList, rechnungList);

        assertThat(rechnen).isEqualTo(List.of());
    }


    @Test
    @DisplayName("rechnen with nicht empty person list")
    void test_rechnen_personal_bill_4(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Rechnung r1 = new Rechnung("1", Money.of(50, "EUR"),a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(50, "EUR"),a, List.of(a, c));
        List<Person> personList = List.of(a,b,c);
        List<Rechnung> rechnungList = List.of(r1,r2);
        SplitterService splitterService = new SplitterService();

        List<PersonalBill> rechnen = splitterService.rechnenBill(personList, rechnungList);

        assertThat(rechnen).isEqualTo(List.of(new PersonalBill(a, Money.of(50.00, "EUR")),new PersonalBill(b, Money.of(-25, "EUR")),new PersonalBill(c, Money.of(-25, "EUR"))));
    }

    @Test
    @DisplayName("splitter with empty person list")
    void test_splitter_1(){
        SplitterService splitterService = new SplitterService();
        List<PersonalBill> rechnen = List.of();
        List<Result> resultList = splitterService.splitter(rechnen);

        assertThat(resultList).isEqualTo(List.of());
    }

    @Test
    @DisplayName("splitter with person list")
    void test_splitter_2(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        SplitterService splitterService = new SplitterService();
        List<PersonalBill> rechnen = List.of(new PersonalBill(a, Money.of(50.00, "EUR"))
                ,new PersonalBill(b,Money.of(-25,"EUR"))
                ,new PersonalBill(c,Money.of(-25,"EUR")));
        List<Result> resultList = splitterService.splitter(rechnen);

        assertThat(resultList).isEqualTo(List.of(new Result(a,b,Money.of(25.00, "EUR")),new Result(a,c,Money.of(25.00, "EUR"))));
    }


    @Test
    @DisplayName("splitter with person list2")
    void test_splitter_3(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        SplitterService splitterService = new SplitterService();
        List<PersonalBill> rechnen = List.of(new PersonalBill(a,Money.of(656.67,"EUR"))
                ,new PersonalBill(b,Money.of(-363.33,"EUR")),new PersonalBill(c,Money.of(-293.33,"EUR")));
        List<Result> resultList = splitterService.splitter(rechnen);

        assertThat(resultList).isEqualTo(List.of(new Result(a,b,Money.of(363.33, "EUR")),new Result(a,c,Money.of(293.33, "EUR"))));
    }



    @Test
    void test_1() {

        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");

        Rechnung r1 = new Rechnung("1", Money.of(50, "EUR"),a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(50, "EUR"),a, List.of(a, c));

        List<Person> personList = List.of(a,b,c);
        List<Rechnung> rechnungList = List.of(r1,r2);

        SplitterService u = new SplitterService();

        assertThat(u.rechnenBill(personList, rechnungList)).isEqualTo(List.of(new PersonalBill(a, Money.of(50, "EUR")),
                new PersonalBill(b, Money.of(-25, "EUR")), new PersonalBill(c, Money.of(-25, "EUR"))));
    }

    @Test
    @DisplayName("with empty rechnung gruppe")
    void test_result_1(){
        Gruppe gruppe = new Gruppe(1,"n",List.of(new Person("a")));
        SplitterService splitterService = new SplitterService();
        List<Result> results = splitterService.result(gruppe);

        assertThat(results).isEqualTo(List.of());

    }

    @Test
    @DisplayName("with empty person gruppe")
    void test_result_2(){
        Gruppe gruppe = new Gruppe(1,"n",List.of());
        SplitterService splitterService = new SplitterService();
        List<Result> results = splitterService.result(gruppe);

        assertThat(results).isEqualTo(List.of());

    }





    @Test
    @DisplayName("Szenario 1: Summieren von Auslagen")
    void test_s1() {


        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1", Money.of(10, "EUR"), a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(20, "EUR"), a, List.of(a, b));

        Gruppe group1 = new Gruppe(1,"group1",List.of(a,b));

        group1.getRechnungList().add(r1);
        group1.getRechnungList().add(r2);

        SplitterService u = new SplitterService();

        List<Result> results = u.result(group1);

        assertThat(results).isEqualTo(List.of(new Result(a, b, Money.of(15, "EUR"))));


    }




    @Test
    @DisplayName("Szenario 2: Ausgleich")
    void test_s2() {
        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1", Money.of(10, "EUR"), a, List.of(a, b));
        Rechnung r2 = new Rechnung("2", Money.of(20, "EUR"), b, List.of(a, b));

        Gruppe group1 = new Gruppe(1,"group1",List.of(a,b));

        group1.getRechnungList().add(r1);
        group1.getRechnungList().add(r2);

        SplitterService u = new SplitterService();
        List<Result> results = u.result(group1);

        assertThat(results).isEqualTo(List.of(new Result(b, a, Money.of(5, "EUR"))));
    }


    @Test
    @DisplayName("Szenario 3: Zahlung ohne eigene Beteiligung")
    void test_s3() {
        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1", Money.of(10, "EUR"), a, List.of(b));
        Rechnung r2 = new Rechnung("2", Money.of(20, "EUR"), a, List.of(a, b));

        Gruppe group1 = new Gruppe(1,"group1",List.of(a,b));

        SplitterService u = new SplitterService();

        group1.getRechnungList().add(r1);
        group1.getRechnungList().add(r2);

        List<Result> results = u.result(group1);

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

        Gruppe group1 = new Gruppe(1,"group1",List.of(a,b,c));

        SplitterService u = new SplitterService();

        group1.getRechnungList().add(r1);
        group1.getRechnungList().add(r2);
        group1.getRechnungList().add(r3);


        List<Result> results = u.result(group1);

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

        Gruppe group1 = new Gruppe(1,"group1",List.of(a,b,c));

        SplitterService u = new SplitterService();

        group1.getRechnungList().add(r1);
        group1.getRechnungList().add(r2);
        group1.getRechnungList().add(r3);

        List<Result> results = u.result(group1);

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


        Gruppe group1 = new Gruppe(1,"group1",List.of(a,b,c,d,e,f));

        SplitterService u = new SplitterService();

        group1.getRechnungList().add(r1);
        group1.getRechnungList().add(r2);
        group1.getRechnungList().add(r3);
        group1.getRechnungList().add(r4);
        group1.getRechnungList().add(r5);
        group1.getRechnungList().add(r6);

        List<Result> results = u.result(group1);

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


        Gruppe group1 = new Gruppe(1,"group1",List.of(a,b,c,d,e,f,g));

        SplitterService u = new SplitterService();

        group1.getRechnungList().add(r1);
        group1.getRechnungList().add(r2);
        group1.getRechnungList().add(r3);
        group1.getRechnungList().add(r4);
        group1.getRechnungList().add(r5);
        group1.getRechnungList().add(r6);
        group1.getRechnungList().add(r7);
        group1.getRechnungList().add(r8);


        List<Result> results = u.result(group1);
        System.out.println(results);

        assertThat(results).isEqualTo(List.of());
    }





}












