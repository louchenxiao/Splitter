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
    public String toString() {
        return "Mid{" +
                "person=" + person +
                ", geld=" + geld +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalBill personalBill)) return false;
        return Objects.equals(getPerson(), personalBill.getPerson()) && Objects.equals(getGeld(), personalBill.getGeld());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson(), getGeld());
    }
}
