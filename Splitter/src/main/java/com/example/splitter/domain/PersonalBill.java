package com.example.splitter.domain;

import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.Objects;

public class PersonalBill {
    private final String person;
    private BigDecimal geld;

    public PersonalBill(String name, BigDecimal geld) {
        this.person = name;
        this.geld = geld;
    }



    public String getPerson() {
        return person;
    }

    public BigDecimal getGeld() {
        return geld;
    }

    public void setGeld(BigDecimal geld) {
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
