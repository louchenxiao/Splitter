package com.example.splitter.repository;

import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class RechnungRepo {
    private List<Rechnung> rechnungList=new ArrayList<>();

    public List<Rechnung> findByGruppeId(Integer integer){
        return rechnungList.stream().filter(e->e.getGruppeId()==integer).toList();
    }

    public Rechnung create(Integer gruppeId, String name, Money geld, Person payer, List<Person> personList){
        Rechnung rechnung=new Rechnung(gruppeId,name,geld,payer,personList);
        rechnungList.add(rechnung);
        return rechnung;
    }

}
