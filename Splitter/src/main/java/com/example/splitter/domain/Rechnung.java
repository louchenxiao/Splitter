package com.example.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;
import java.util.Objects;

public class Rechnung {
    private String rechnungName;
    private Money geld;
    private Person payer;
    private List<Person> persons;

    public Rechnung(String rechnungName, Money geld, Person payer, List<Person> persons) {
        this.rechnungName = rechnungName;
        this.geld = geld;
        this.payer = payer;
        this.persons = persons;
    }

    public String getRechnungName() {
        return rechnungName;
    }

    public Money getGeld() {
        return geld;
    }

    public Person getPayer() {
        return payer;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setRechnungName(String rechnungName) {
        this.rechnungName = rechnungName;
    }

    public void setGeld(Money geld) {
        this.geld = geld;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rechnung rechnung)) return false;
        return Objects.equals(getRechnungName(), rechnung.getRechnungName()) && Objects.equals(getGeld(), rechnung.getGeld()) && Objects.equals(getPayer(), rechnung.getPayer()) && Objects.equals(getPersons(), rechnung.getPersons());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRechnungName(), getGeld(), getPayer(), getPersons());
    }

    @Override
    public String toString() {
        return "Rechnung{" +
                "rechnungName='" + rechnungName + '\'' +
                ", geld=" + geld +
                ", payer=" + payer +
                ", persons=" + persons +
                '}';
    }
}
