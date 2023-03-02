package com.example.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.Objects;

public class Mid {
    private Person person;
    private Money geld;

    public Mid(Person name, Money geld) {
        this.person = name;
        this.geld = geld;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
        if (!(o instanceof Mid mid)) return false;
        return Objects.equals(getPerson(), mid.getPerson()) && Objects.equals(getGeld(), mid.getGeld());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson(), getGeld());
    }
}
