package com.example.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;
import java.util.Objects;

public record Rechnung(String rechnungName, Money geld, Person payer, List<Person> persons) {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rechnung rechnung)) return false;
        return Objects.equals(rechnungName(), rechnung.rechnungName()) && Objects.equals(geld(), rechnung.geld()) && Objects.equals(payer(), rechnung.payer()) && Objects.equals(persons(), rechnung.persons());
    }

    @Override
    public int hashCode() {
        return Objects.hash(rechnungName(), geld(), payer(), persons());
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
