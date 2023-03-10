package com.example.splitter.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gruppe {
    private final Integer id;
    private final String name;
    private final List<Person> personList;
    private final List<Rechnung> rechnungList;
    private Boolean geschlossen;
    private final LocalDateTime localDateTime;

    public Gruppe(Integer id, String gruppeName, List<Person> personList) {
        this.id = id;
        this.name = gruppeName;
        this.personList = personList;
        this.rechnungList = new ArrayList<>();
        this.geschlossen = false;
        this.localDateTime = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public List<Rechnung> getRechnungList() {
        return rechnungList;
    }

    public Boolean getGeschlossen() {
        return geschlossen;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gruppe gruppe)) return false;
        return Objects.equals(getId(), gruppe.getId()) && Objects.equals(getName(), gruppe.getName()) && Objects.equals(getPersonList(), gruppe.getPersonList()) && Objects.equals(getRechnungList(), gruppe.getRechnungList()) && Objects.equals(getGeschlossen(), gruppe.getGeschlossen()) && Objects.equals(getLocalDateTime(), gruppe.getLocalDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPersonList(), getRechnungList(), getGeschlossen(), getLocalDateTime());
    }

    public void setGeschlossen() {
        this.geschlossen = true;
    }

    @Override
    public String toString() {
        return "Gruppe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personList=" + personList +
                ", rechnungList=" + rechnungList +
                ", geschlossen=" + geschlossen +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
