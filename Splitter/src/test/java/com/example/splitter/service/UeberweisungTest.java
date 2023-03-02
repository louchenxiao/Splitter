package com.example.splitter.service;

import com.example.splitter.domain.Mid;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.domain.Result;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class UeberweisungTest {

    @Test
    void test_1(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");

        Rechnung r1 = new Rechnung("1",Money.of(50,"EUR"),a,List.of(a,b));
        Rechnung r2 = new Rechnung("2",Money.of(50,"EUR"),a,List.of(a,c));



        Ueberweisung u = new Ueberweisung(List.of(r1,r2),List.of(a,b,c));
        assertThat(u.rechnen()).isEqualTo(List.of(new Mid(a,Money.of(50,"EUR")),
                new Mid(b,Money.of(-25,"EUR")),new Mid(c,Money.of(-25,"EUR"))));
    }

    @Test
    @DisplayName("Szenario 1: Summieren von Auslagen")
    void test_s1(){
        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1",Money.of(10,"EUR"),a,List.of(a,b));
        Rechnung r2 = new Rechnung("2",Money.of(20,"EUR"),a,List.of(a,b));

        Ueberweisung u = new Ueberweisung(List.of(r1,r2),List.of(a,b));

        List<Result> results = u.allInOne();

        assertThat(results).isEqualTo(List.of(new Result(a,b,Money.of(15,"EUR"))));


    }

    @Test
    @DisplayName("Szenario 2: Ausgleich")
    void test_s2(){
        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1",Money.of(10,"EUR"),a,List.of(a,b));
        Rechnung r2 = new Rechnung("2",Money.of(20,"EUR"),b,List.of(a,b));

        Ueberweisung u = new Ueberweisung(List.of(r1,r2),List.of(a,b));

        List<Result> results = u.allInOne();

        assertThat(results).isEqualTo(List.of(new Result(b,a,Money.of(5,"EUR"))));
    }

    @Test
    @DisplayName("Szenario 3: Zahlung ohne eigene Beteiligung")
    void test_s3(){
        Person a = new Person("a");
        Person b = new Person("b");
        Rechnung r1 = new Rechnung("1",Money.of(10,"EUR"),a,List.of(b));
        Rechnung r2 = new Rechnung("2",Money.of(20,"EUR"),a,List.of(a,b));

        Ueberweisung u = new Ueberweisung(List.of(r1,r2),List.of(a,b));

        List<Result> results = u.allInOne();

        assertThat(results).isEqualTo(List.of(new Result(a,b,Money.of(20,"EUR"))));
    }

    @Test
    @DisplayName("Szenario 4: Ringausgleich")
    void test_s4(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Rechnung r1 = new Rechnung("1",Money.of(10,"EUR"),a,List.of(a,b));
        Rechnung r2 = new Rechnung("2",Money.of(10,"EUR"),b,List.of(c,b));
        Rechnung r3 = new Rechnung("3",Money.of(10,"EUR"),c,List.of(a,c));

        Ueberweisung u = new Ueberweisung(List.of(r1,r2,r3),List.of(a,b,c));

        List<Result> results = u.allInOne();

        assertThat(results).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Szenario 5: ABC Beispiel aus der Einführung")
    void test_s5(){
        Person a = new Person("Anton");
        Person b = new Person("Berta");
        Person c = new Person("Christian");
        Rechnung r1 = new Rechnung("1",Money.of(60,"EUR"),a,List.of(a,b,c));
        Rechnung r2 = new Rechnung("2",Money.of(30,"EUR"),b,List.of(a,c,b));
        Rechnung r3 = new Rechnung("3",Money.of(100,"EUR"),c,List.of(b,c));

        Ueberweisung u = new Ueberweisung(List.of(r1,r2,r3),List.of(a,b,c));

        List<Result> results = u.allInOne();

        assertThat(results).isEqualTo(List.of(new Result(a,b,Money.of(30,"EUR")),new Result(c,b,Money.of(20,"EUR"))));
    }

    @Test
    @DisplayName("Szenario 6: Beispiel aus der Aufgabenstellung")
    void test_s6(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Rechnung r1 = new Rechnung("Hotelzimmer",Money.of(564,"EUR"),a,List.of(a,b,c,d,e,f));
        Rechnung r2 = new Rechnung("Benzin (Hinweg)",Money.of(38.58,"EUR"),b,List.of(a,b));
        Rechnung r3 = new Rechnung("Benzin (Rückweg)",Money.of(38.58,"EUR"),b,List.of(a,b,d));
        Rechnung r4 = new Rechnung("Benzin",Money.of(82.11,"EUR"),c,List.of(c,e,f));
        Rechnung r5 = new Rechnung("Städtetour",Money.of(96,"EUR"),d,List.of(a,b,c,d,e,f));
        Rechnung r6 = new Rechnung("Theatervorstellung",Money.of(95.37,"EUR"),f,List.of(b,e,f));


        Ueberweisung u = new Ueberweisung(List.of(r1,r2,r3,r4,r5,r6),List.of(a,b,c,d,e,f));

        List<Result> results = u.allInOne();

        assertThat(results).isEqualTo(List.of(
                new Result(a,b,Money.of(96.78,"EUR")),
                new Result(a,c,Money.of(55.26,"EUR")),
                new Result(a,d,Money.of(26.86,"EUR")),
                new Result(a,e,Money.of(169.16,"EUR")),
                new Result(a,f,Money.of(73.79,"EUR"))));
    }

    @Test
    @DisplayName("Szenario 7: Minimierung")
    @Disabled
    void test_s7(){
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Person g = new Person("g");

        Rechnung r1 = new Rechnung("1",Money.of(20,"EUR"),d,List.of(d,f));
        Rechnung r2 = new Rechnung("2",Money.of(10,"EUR"),g,List.of(b));
        Rechnung r3 = new Rechnung("3",Money.of(75,"EUR"),e,List.of(a,c,e));
        Rechnung r4 = new Rechnung("4",Money.of(50,"EUR"),f,List.of(a,f));
        Rechnung r5 = new Rechnung("5",Money.of(40,"EUR"),e,List.of(d));
        Rechnung r6 = new Rechnung("6",Money.of(40,"EUR"),f,List.of(b,f));
        Rechnung r7 = new Rechnung("7",Money.of(5,"EUR"),f,List.of(c));
        Rechnung r8 = new Rechnung("8",Money.of(30,"EUR"),g,List.of(a));


        Ueberweisung u = new Ueberweisung(List.of(r1,r2,r3,r4,r5,r6,r7,r8),List.of(a,b,c,d,e,f,g));

        List<Result> results = u.allInOne();
        System.out.println(results);

        assertThat(results).isEqualTo(List.of());
    }




}