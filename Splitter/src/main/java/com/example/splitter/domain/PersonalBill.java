package com.example.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.Objects;

public class PersonalBill {
    private final Person person;
    private Money geld;

    public PersonalBill(Person name, Money geld) {
        this.person = name;
        this.geld = geld;
    }

    public Person getPerson() {
        return person;
    }

    public Money getGeld() {
        return geld;
    }

    public void setGeld(Money geld) {
        this.geld = geld;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalBill that = (PersonalBill) o;
        return Objects.equals(person, that.person) && Objects.equals(geld, that.geld);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, geld);
    }

    @Override
    public String toString() {
        return "PersonalBill{" +
                "person=" + person +
                ", geld=" + geld +
                '}';
    }
}
