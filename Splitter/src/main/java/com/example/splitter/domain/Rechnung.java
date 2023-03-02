package com.example.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;

public class Rechnung {
    private String name;
    private Money geld;
    private Person payer;
    private List<Person> persons;

    public Rechnung(String name, Money geld, Person payer, List<Person> persons) {
        this.name = name;
        this.geld = geld;
        this.payer = payer;
        this.persons = persons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getGeld() {
        return geld;
    }

    public void setGeld(Money geld) {
        this.geld = geld;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
