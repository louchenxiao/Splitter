package com.example.splitter.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;




public class Rechnung{

    private final String name;
    private final BigDecimal money;
    private final String payer;
    private final Set<String> persons;


    public Rechnung(String name, BigDecimal money, String payer, Set<String> persons) {
        this.name = name;
        this.money = money;
        this.payer = payer;
        this.persons = persons;
    }


    public Set<String> getPersons() {
        return persons;
    }

    public String getName() {
        return name;
    }


    public BigDecimal getMoney() {
        return money;
    }



    public String getPayer() {
        return payer;
    }


    @Override
    public String toString() {
        return "Rechnung{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", payer='" + payer + '\'' +
                ", persons=" + persons +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rechnung rechnung = (Rechnung) o;
        return Objects.equals(name, rechnung.name) && Objects.equals(money, rechnung.money) && Objects.equals(payer, rechnung.payer) && Objects.equals(persons, rechnung.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, money, payer, persons);
    }
}
