package com.example.splitter.service;

import com.example.splitter.domain.Mid;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.domain.Result;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;


import java.math.RoundingMode;
import java.util.*;

@Service
public class Ueberweisung {
    private List<Rechnung> rechnungList;
    private List<Person> personList;

    public Ueberweisung(List<Rechnung> rechnungList,List<Person> personList) {
        this.rechnungList = rechnungList;
        this.personList= personList;
    }


    private List<Mid> money(){
        List<Mid> money = new ArrayList<>();
        for (Person p :personList){
            money.add(new Mid(p,Money.of(0,"EUR")));
        }
        return money;
    }


    public List<Mid> rechnen(){
        List<Mid> money = money();
        for (Rechnung r: rechnungList) {
            Double zahl = (r.getGeld().divide(r.getPersons().size())).getNumberStripped().setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            Money each = Money.of(zahl,"EUR");
            for (Mid m:money) {
                if(m.getPerson().equals(r.getPayer())){
                    Money geld = m.getGeld();
                    m.setGeld(r.getGeld().add(geld));
                }
                if(r.getPersons().contains(m.getPerson())){
                    m.setGeld(m.getGeld().subtract(each));
                }
            }
        }
        return money;
    }





    public List<Result> second(List<Mid> midList){
        List<Mid> receiver= midList.stream().filter(e -> e.getGeld().isPositive()).toList();
        List<Mid> giver = midList.stream().filter(e -> e.getGeld().isNegative()).toList();
        List<Result> results = new ArrayList<>();
        for (int i = 0; i < receiver.size(); i++) {
                for (int j = 0; j < giver.size(); j++) {
                    if (receiver.get(i).getGeld().isGreaterThanOrEqualTo(giver.get(j).getGeld().abs())) {
                        results.add(new Result(receiver.get(i).getPerson(),giver.get(j).getPerson(),giver.get(j).getGeld().abs()));
                        receiver.get(i).setGeld(receiver.get(i).getGeld().add(giver.get(j).getGeld()) );
                        giver.get(j).setGeld(Money.of(0,"EUR"));
                    } else {
                        results.add(new Result(receiver.get(i).getPerson(),giver.get(j).getPerson(),receiver.get(i).getGeld().abs()));
                        giver.get(j).setGeld(giver.get(j).getGeld().add(receiver.get(i).getGeld()) );
                        receiver.get(i).setGeld(Money.of(0,"EUR"));
                    }
            }
        }
        return results;
    }

    public List<Result> allInOne(){
        return second(rechnen()).stream().filter(e->!e.getMoney().isZero()).toList();
    }

}
