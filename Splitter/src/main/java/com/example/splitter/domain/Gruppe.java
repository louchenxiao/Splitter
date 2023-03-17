package com.example.splitter.domain;





import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.math.BigDecimal;
import java.util.*;


public class Gruppe {
    @Id
    private final Integer id;
    private final String name;
    private  List<String> personen;
    private  Set<Rechnung> rechnungSet;
    private Boolean geschlossen;
    @PersistenceCreator
    public Gruppe(Integer id, String name, List<String> personen, Set<Rechnung> rechnungSet, Boolean geschlossen) {
        this.id = id;
        this.name = name;
        this.personen = personen;
        this.rechnungSet = rechnungSet;
        this.geschlossen = geschlossen;
    }

    public Gruppe(String name, List<String> personen){
        this(null,name,personen,new HashSet<>(),false);
    }

    public static Gruppe createGruppe(String gruppeName, List<String> personList) {
        return new Gruppe(null, gruppeName, personList, new HashSet<>(), false);
    }

    public void addRechnung(String name, BigDecimal money, String payer, Set<String> persons){
        rechnungSet.add(new Rechnung( name,  money,  payer,  persons));
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPersonen() {
        return personen;
    }

    public void setPersonen(List<String> personen) {
        this.personen = personen;
    }

    public Set<Rechnung> getRechnungSet() {
        return rechnungSet;
    }

    public void setRechnungSet(Set<Rechnung> rechnungSet) {
        this.rechnungSet = rechnungSet;
    }

    public Boolean getGeschlossen() {
        return geschlossen;
    }

    public void setGeschlossen(Boolean geschlossen) {
        this.geschlossen = geschlossen;
    }

    @Override
    public String toString() {
        return "Gruppe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personen=" + personen +
                ", rechnungSet=" + rechnungSet +
                ", geschlossen=" + geschlossen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gruppe gruppe = (Gruppe) o;
        return Objects.equals(id, gruppe.id) && Objects.equals(name, gruppe.name) && Objects.equals(personen, gruppe.personen) && Objects.equals(rechnungSet, gruppe.rechnungSet) && Objects.equals(geschlossen, gruppe.geschlossen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, personen, rechnungSet, geschlossen);
    }
}



