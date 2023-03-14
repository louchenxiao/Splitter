package com.example.splitter.domain;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Gruppe {
    private final Integer id;
    private final String name;
    private final List<Person> personen;
    private final List<Rechnung> rechnungList;
    private Boolean geschlossen;

    public Gruppe(Integer id, String gruppeName, List<Person> personen) {
        this.id = id;
        this.name = gruppeName;
        this.personen = personen;
        this.rechnungList=new ArrayList<>();
        this.geschlossen = false;
    }


    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public List<Person> getPersonen() {
        return personen;
    }

    public List<Rechnung> getRechnungList() {
        return rechnungList;
    }

    public Boolean getGeschlossen() {
        return geschlossen;
    }

    public void setGeschlossen() {
        this.geschlossen = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gruppe gruppe)) return false;
        return Objects.equals(getId(), gruppe.getId()) && Objects.equals(getName(), gruppe.getName()) && Objects.equals(getPersonen(), gruppe.getPersonen()) && Objects.equals(getRechnungList(), gruppe.getRechnungList()) && Objects.equals(getGeschlossen(), gruppe.getGeschlossen());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPersonen(), getRechnungList(), getGeschlossen());
    }

    @Override
    public String toString() {
        return "Gruppe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personen=" + personen +
                ", rechnungList=" + rechnungList +
                ", geschlossen=" + geschlossen +
                '}';
    }
}
