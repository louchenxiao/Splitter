package com.example.splitter.service;

import com.example.splitter.domain.*;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;


import java.math.RoundingMode;
import java.util.*;

@Service
public class SplitterService {
    public List<PersonalBill> init(List<Person> personList){
        List<PersonalBill> money = new ArrayList<>();
        for (Person p :personList){
            money.add(new PersonalBill(p,Money.of(0,"EUR")));
        }
        return money;
    }


    public List<PersonalBill> rechnenBill(List<Person> personList, List<Rechnung> rechnungList){
        List<PersonalBill> money = init(personList);
        for (Rechnung r: rechnungList) {
            Double zahl = (r.geld().divide(r.persons().size())).getNumberStripped().setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            Money each = Money.of(zahl,"EUR");
            for (PersonalBill m:money) {
                if(m.getPerson().equals(r.payer())){
                    Money geld = m.getGeld();
                    m.setGeld(r.geld().add(geld));
                }
                if(r.persons().stream().map(Person::getName).toList().contains(m.getPerson().getName())){
                    m.setGeld(m.getGeld().subtract(each));
                }
            }
        }
        return money;
    }

    public List<Result> splitter(List<PersonalBill> personalBillList){
        List<PersonalBill> receiver= personalBillList.stream().filter(e -> e.getGeld().isPositive()).toList();
        List<PersonalBill> giver = personalBillList.stream().filter(e -> e.getGeld().isNegative()).toList();
        List<Result> results = new ArrayList<>();
        for (PersonalBill personalBill : receiver) {
            for (PersonalBill bill : giver) {
                if (personalBill.getGeld().isGreaterThanOrEqualTo(bill.getGeld().abs())) {
                    results.add(new Result(personalBill.getPerson(), bill.getPerson(), bill.getGeld().abs()));
                    personalBill.setGeld(personalBill.getGeld().add(bill.getGeld()));
                    bill.setGeld(Money.of(0, "EUR"));
                } else {
                    if(personalBill.getGeld().abs().isPositive()){
                        results.add(new Result(personalBill.getPerson(), bill.getPerson(), personalBill.getGeld().abs()));
                    }
                    bill.setGeld(bill.getGeld().add(personalBill.getGeld()));
                    personalBill.setGeld(Money.of(0, "EUR"));
                }
            }
        }
        return results;
    }

    public List<Result> result(Gruppe gruppe){
        List<Person> personList = gruppe.getPersonen();
        List<Rechnung> rechnungList = gruppe.getRechnungList();
        return splitter(rechnenBill(personList,rechnungList));
    }

}
