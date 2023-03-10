package com.example.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;

public class Rechnung {
    private Integer gruppeId;
    private String rechnungName;
    private Money geld;
    private Person payer;
    private List<Person> persons;

    public Rechnung(Integer gruppeId, String rechnungName, Money geld, Person payer, List<Person> persons) {
        this.gruppeId = gruppeId;
        this.rechnungName = rechnungName;
        this.geld = geld;
        this.payer = payer;
        this.persons = persons;
    }

    public Integer getGruppeId() {
        return gruppeId;
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

    @Override
    public String toString() {
        return "Rechnung{" +
                "gruppeId=" + gruppeId +
                ", rechnungName='" + rechnungName + '\'' +
                ", geld=" + geld +
                ", payer=" + payer +
                ", persons=" + persons +
                '}';
    }
}
