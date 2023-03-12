package com.example.splitter.service;

import com.example.splitter.domain.*;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;


import java.math.RoundingMode;
import java.util.*;

@Service
public class Ueberweisung {
    public List<PersonalBill> money(List<Person> personList){
        List<PersonalBill> money = new ArrayList<>();
        for (Person p :personList){
            money.add(new PersonalBill(p,Money.of(0,"EUR")));
        }
        return money;
    }


    public List<PersonalBill> rechnen(List<Person> personList, List<Rechnung> rechnungList){
        List<PersonalBill> money = money(personList);
        for (Rechnung r: rechnungList) {
            Double zahl = (r.getGeld().divide(r.getPersons().size())).getNumberStripped().setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            Money each = Money.of(zahl,"EUR");
            for (PersonalBill m:money) {
                if(m.getPerson().equals(r.getPayer())){
                    Money geld = m.getGeld();
                    m.setGeld(r.getGeld().add(geld));
                }
                if(r.getPersons().stream().map(person -> person.getName()).toList().contains(m.getPerson().getName())){
                    m.setGeld(m.getGeld().subtract(each));
                }
            }
        }
        return money;
    }

    public List<Result> second(List<PersonalBill> personalBillList){
        List<PersonalBill> receiver= personalBillList.stream().filter(e -> e.getGeld().isPositive()).toList();
        List<PersonalBill> giver = personalBillList.stream().filter(e -> e.getGeld().isNegative()).toList();
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

    public List<Result> result(Gruppe gruppe){
        List<Person> personList = gruppe.getPersonen();
        List<Rechnung> rechnungList = gruppe.getRechnungList();
        return second(rechnen(personList,rechnungList)).stream().filter(e->!e.getMoney().isZero()).toList();
    }

}
