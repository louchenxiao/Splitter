package com.example.splitter.domain;


import java.time.LocalDateTime;
import java.util.List;

public class Gruppe {
    private final Integer id;
    private final List<Person> personList;
    private final List<Rechnung> rechnungList;
    private Boolean openStatus;
    private final LocalDateTime localDateTime;

    public Gruppe(Integer id, List<Person> personList, List<Rechnung> rechnungList, Boolean openStatus, LocalDateTime localDateTime) {
        this.id = id;
        this.personList = personList;
        this.rechnungList = rechnungList;
        this.openStatus = openStatus;
        this.localDateTime = localDateTime;
    }

    public Integer getId() {
        return id;
    }

    public List<Rechnung> getRechnungList() {
        return rechnungList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public Boolean getOpenStatus() {
        return openStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setOpenStatus() {
        this.openStatus = false;
    }
}
