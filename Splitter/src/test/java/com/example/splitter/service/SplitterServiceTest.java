package com.example.splitter.service;
import com.example.splitter.domain.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class SplitterServiceTest {
    @Test
    @DisplayName("init with not empty list")
    void test_init_1(){
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Person c = new Person(3,"c",List.of());
        List<Person> personList = List.of(a,b,c);
        SplitterService splitterService = new SplitterService();
        List<PersonalBill> init = splitterService.init(personList);
        assertThat(init).isEqualTo(List.of(new PersonalBill("a",new BigDecimal(0))
                ,new PersonalBill("b",new BigDecimal(0))
                ,new PersonalBill("c",new BigDecimal(0))));
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
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Person c = new Person(3,"c",List.of());
        List<Person> personList = List.of(a,b,c);
        List<Rechnung> rechnungList = List.of();
        SplitterService splitterService = new SplitterService();

        List<PersonalBill> rechnen = splitterService.rechnenBill(personList, rechnungList);

        assertThat(rechnen).isEqualTo(List.of(new PersonalBill("a", new BigDecimal(0))
                ,new PersonalBill("b", new BigDecimal(0))
                ,new PersonalBill("c", new BigDecimal(0))));
    }

    @Test
    @DisplayName("rechnen with empty person list")
    void test_rechnen_personal_bill_3(){
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Person c = new Person(3,"c",List.of());
        Rechnung r1 = new Rechnung("1", new BigDecimal(50),"a", Set.of("a","b"));
        Rechnung r2 = new Rechnung("2", new BigDecimal(50),"b", Set.of("a","b"));
        List<Person> personList = List.of();
        List<Rechnung> rechnungList = List.of(r1,r2);
        SplitterService splitterService = new SplitterService();

        List<PersonalBill> rechnen = splitterService.rechnenBill(personList, rechnungList);

        assertThat(rechnen).isEqualTo(List.of());
    }


    @Test
    @DisplayName("rechnen with nicht empty person list")
    void test_rechnen_personal_bill_4(){
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Person c = new Person(3,"c",List.of());
        Rechnung r1 = new Rechnung("1", (new BigDecimal("50.00").setScale(2,RoundingMode.HALF_UP)),"a", Set.of("a","b"));
        Rechnung r2 = new Rechnung("2", (new BigDecimal("50.00").setScale(2,RoundingMode.HALF_UP)),"b", Set.of("a","c"));
        List<Person> personList = List.of(a,b,c);
        List<Rechnung> rechnungList = List.of(r1,r2);
        SplitterService splitterService = new SplitterService();

        List<PersonalBill> rechnen = splitterService.rechnenBill(personList, rechnungList);

        assertThat(rechnen).isEqualTo(List.of(new PersonalBill("a",(new BigDecimal("0.00").setScale(2,RoundingMode.HALF_UP)))
        ,new PersonalBill("b",(new BigDecimal("25.00").setScale(2,RoundingMode.HALF_UP)))
        ,new PersonalBill("c",(new BigDecimal("-25.00").setScale(2,RoundingMode.HALF_UP)))));
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
        SplitterService splitterService = new SplitterService();
        List<PersonalBill> rechnen = List.of(new PersonalBill("a", new BigDecimal("50"))
                ,new PersonalBill("b",new BigDecimal("-25"))
                ,new PersonalBill("c",new BigDecimal("-25")));
        List<Result> resultList = splitterService.splitter(rechnen);

        assertThat(resultList).isEqualTo(List.of(new Result("a","b",new BigDecimal("25"))
                ,new Result("a","c",new BigDecimal("25"))));
    }


    @Test
    @DisplayName("splitter with person list 2")
    void test_splitter_3(){
        SplitterService splitterService = new SplitterService();
        List<PersonalBill> rechnen = List.of(new PersonalBill("a",new BigDecimal("656.67"))
                ,new PersonalBill("b",new BigDecimal("-363.33"))
                ,new PersonalBill("c",new BigDecimal("-293.33")));
        List<Result> resultList = splitterService.splitter(rechnen);

        assertThat(resultList).isEqualTo(List.of(new Result("a","b",new BigDecimal("363.33"))
                ,new Result("a","c",new BigDecimal("293.33"))));
    }



    @Test
    void test_1() {

        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Person c = new Person(3,"c",List.of());

        Rechnung r1 = new Rechnung("1", (new BigDecimal("50.00").setScale(2,RoundingMode.HALF_UP)),"a", Set.of("a", "b"));
        Rechnung r2 = new Rechnung("2", (new BigDecimal("50.00").setScale(2,RoundingMode.HALF_UP)),"a", Set.of("a", "c"));

        List<Person> personList = List.of(a,b,c);
        List<Rechnung> rechnungList = List.of(r1,r2);

        SplitterService u = new SplitterService();

        assertThat(u.rechnenBill(personList, rechnungList)).isEqualTo(List.of(new PersonalBill("a", (new BigDecimal("50.00").setScale(2,RoundingMode.HALF_UP))),
                new PersonalBill("b", (new BigDecimal("-25.00").setScale(2,RoundingMode.HALF_UP))), new PersonalBill("c", (new BigDecimal("-25.00").setScale(2,RoundingMode.HALF_UP)))));
    }

    @Test
    @DisplayName("with empty rechnung gruppe")
    void test_result_1(){
        Gruppe gruppe = new Gruppe(1,"n",List.of("a"),Set.of(),false);
        SplitterService splitterService = new SplitterService();
        List<Person> personList = List.of(new Person(1,"a",List.of(1)));
        List<Result> results = splitterService.result(personList,gruppe.getRechnungSet().stream().toList());
        assertThat(results).isEqualTo(List.of());

    }
    @Test
    @DisplayName("with empty person gruppe")
    void test_result_2(){
        Gruppe gruppe = new Gruppe(1,"n",List.of(),Set.of(),false);
        SplitterService splitterService = new SplitterService();
        List<Result> results = splitterService.result(List.of(),List.of());
        assertThat(results).isEqualTo(List.of());

    }





    @Test
    @DisplayName("Szenario 1: Summieren von Auslagen")
    void test_s1() {
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Rechnung r1 = new Rechnung("1", (new BigDecimal("10.00").setScale(2,RoundingMode.HALF_UP)), "a", Set.of("a", "b"));
        Rechnung r2 = new Rechnung("2", (new BigDecimal("20.00").setScale(2,RoundingMode.HALF_UP)), "a", Set.of("a", "b"));

        SplitterService u = new SplitterService();
        List<Result> results = u.result(List.of(a,b),List.of(r1,r2));
        assertThat(results).isEqualTo(List.of(new Result("a", "b", (new BigDecimal("15.00").setScale(2,RoundingMode.HALF_UP)))));
    }



    @Test
    @DisplayName("Szenario 2: Ausgleich")
    void test_s2() {
        Person a = new Person(1,"a",List.of());
        Person b = new Person(1,"b",List.of());
        Rechnung r1 = new Rechnung("1", (new BigDecimal("10.00").setScale(2,RoundingMode.HALF_UP)), "a", Set.of("a", "b"));
        Rechnung r2 = new Rechnung("2", (new BigDecimal("20.00").setScale(2,RoundingMode.HALF_UP)), "b", Set.of("a", "b"));


        SplitterService u = new SplitterService();
        List<Result> results = u.result(List.of(a,b),List.of(r1,r2));

        assertThat(results).isEqualTo(List.of(new Result("b", "a", (new BigDecimal("5.00").setScale(2,RoundingMode.HALF_UP)))));
    }

    @Test
    @DisplayName("Szenario 3: Zahlung ohne eigene Beteiligung")
    void test_s3() {
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Rechnung r1 = new Rechnung("1", (new BigDecimal("10.00").setScale(2,RoundingMode.HALF_UP)), "a", Set.of("b"));
        Rechnung r2 = new Rechnung("2", (new BigDecimal("20.00").setScale(2,RoundingMode.HALF_UP)), "a", Set.of("a", "b"));

        SplitterService u = new SplitterService();
        List<Result> results = u.result(List.of(a,b),List.of(r1,r2));

        assertThat(results).isEqualTo(List.of(new Result("a", "b", (new BigDecimal("20.00").setScale(2,RoundingMode.HALF_UP)))));
    }

    @Test
    @DisplayName("Szenario 4: Ringausgleich")
    void test_s4() {
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Person c = new Person(3,"c",List.of());
        Rechnung r1 = new Rechnung("1", (new BigDecimal("10.00").setScale(2,RoundingMode.HALF_UP)), "a", Set.of("a", "b"));
        Rechnung r2 = new Rechnung("2", (new BigDecimal("10.00").setScale(2,RoundingMode.HALF_UP)), "b", Set.of("c", "b"));
        Rechnung r3 = new Rechnung("3", (new BigDecimal("10.00").setScale(2,RoundingMode.HALF_UP)), "c", Set.of("a", "c"));

        SplitterService u = new SplitterService();

        List<Result> results = u.result(List.of(a,b,c),List.of(r1,r2,r3));

        assertThat(results).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Szenario 5: ABC Beispiel aus der Einführung")
    void test_s5() {
        Person a = new Person(1,"Anton",List.of());
        Person b = new Person(2,"Berta",List.of());
        Person c = new Person(3,"Christian",List.of());
        Rechnung r1 = new Rechnung("1", (new BigDecimal("60.00").setScale(2,RoundingMode.HALF_UP)), "Anton", Set.of("Anton", "Berta", "Christian"));
        Rechnung r2 = new Rechnung("2", (new BigDecimal("30.00").setScale(2,RoundingMode.HALF_UP)), "Berta", Set.of("Anton", "Christian", "Berta"));
        Rechnung r3 = new Rechnung("3",(new BigDecimal("100.00").setScale(2,RoundingMode.HALF_UP)), "Christian", Set.of("Berta", "Christian"));

        SplitterService u = new SplitterService();

        List<Result> results = u.result(List.of(a,b,c),List.of(r1,r2,r3));

        assertThat(results).isEqualTo(List.of(new Result("Anton", "Berta"
                ,(new BigDecimal("30.00").setScale(2,RoundingMode.HALF_UP))), new Result("Christian", "Berta",(new BigDecimal("20.00").setScale(2,RoundingMode.HALF_UP)))));
    }

    @Test
    @DisplayName("Szenario 6: Beispiel aus der Aufgabenstellung")
    void test_s6() {
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Person c = new Person(3,"c",List.of());
        Person d = new Person(4,"d",List.of());
        Person e = new Person(5,"e",List.of());
        Person f = new Person(6,"f",List.of());
        Rechnung r1 = new Rechnung("Hotelzimmer", new BigDecimal("564"), "a", Set.of("a", "b", "c", "d", "e", "f"));
        Rechnung r2 = new Rechnung("Benzin (Hinweg)", new BigDecimal("38.58"), "b", Set.of("a", "b"));
        Rechnung r3 = new Rechnung("Benzin (Rückweg)", new BigDecimal("38.58"), "b", Set.of("a", "b", "d"));
        Rechnung r4 = new Rechnung("Benzin", new BigDecimal("82.11"), "c", Set.of("c", "e", "f"));
        Rechnung r5 = new Rechnung("Städtetour", new BigDecimal("96"), "d", Set.of("a", "b","c", "d", "e", "f"));
        Rechnung r6 = new Rechnung("Theatervorstellung", new BigDecimal("95.37"), "f", Set.of("b", "e", "f"));


        SplitterService u = new SplitterService();

        List<Result> results = u.result(List.of(a,b,c,d,e,f),List.of(r1,r2,r3,r4,r5,r6));

        assertThat(results).isEqualTo(List.of(
                new Result("a", "b", (new BigDecimal("96.78").setScale(2, RoundingMode.HALF_UP))),
                new Result("a", "c", (new BigDecimal("55.26")).setScale(2,RoundingMode.HALF_UP)),
                new Result("a", "d", (new BigDecimal("26.86")).setScale(2,RoundingMode.HALF_UP)),
                new Result("a", "e", (new BigDecimal("169.16")).setScale(2,RoundingMode.HALF_UP)),
                new Result("a", "f", (new BigDecimal("73.79")).setScale(2,RoundingMode.HALF_UP))));
    }

    @Test
    @Disabled
    @DisplayName("Szenario 7: Minimierung")
    void test_s7() {
        Person a = new Person(1,"a",List.of());
        Person b = new Person(2,"b",List.of());
        Person c = new Person(3,"c",List.of());
        Person d = new Person(4,"d",List.of());
        Person e = new Person(5,"e",List.of());
        Person f = new Person(6,"f",List.of());
        Person g = new Person(7,"g",List.of());

        Rechnung r1 = new Rechnung("1", new BigDecimal(20), "d", Set.of("d", "f"));
        Rechnung r2 = new Rechnung("2", new BigDecimal(10), "g", Set.of("b"));
        Rechnung r3 = new Rechnung("3", new BigDecimal(75), "e", Set.of("a", "c", "e"));
        Rechnung r4 = new Rechnung("4", new BigDecimal(50), "f", Set.of("a", "f"));
        Rechnung r5 = new Rechnung("5", new BigDecimal(40), "e", Set.of("d"));
        Rechnung r6 = new Rechnung("6", new BigDecimal(40), "f", Set.of("b", "f"));
        Rechnung r7 = new Rechnung("7", new BigDecimal(5), "f", Set.of("c"));
        Rechnung r8 = new Rechnung("8", new BigDecimal(30), "g", Set.of("a"));

        SplitterService u = new SplitterService();

        List<Result> results = u.result(List.of(a,b,c,d,e,f,g),List.of(r1,r2,r3,r4,r5,r6,r7,r8));
        System.out.println(results);

        assertThat(results).isEqualTo(List.of(new Result("f", "a", (new BigDecimal(40).setScale(2, RoundingMode.HALF_UP))),
                new Result("g", "a", (new BigDecimal(40)).setScale(2,RoundingMode.HALF_UP)),
                new Result("e", "b", (new BigDecimal(30)).setScale(2,RoundingMode.HALF_UP)),
                new Result("e", "c", (new BigDecimal(30)).setScale(2,RoundingMode.HALF_UP)),
                new Result("e", "d", (new BigDecimal(30)).setScale(2,RoundingMode.HALF_UP))));
    }





}














