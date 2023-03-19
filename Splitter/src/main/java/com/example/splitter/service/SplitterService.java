package com.example.splitter.service;

import com.example.splitter.domain.*;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

@Service
public class SplitterService {
    public List<PersonalBill> init(List<Person> personList){
        List<PersonalBill> money = new ArrayList<>();
        for (Person p :personList){
            money.add(new PersonalBill(p.getName(),new BigDecimal(0)));
        }
        return money;
    }


    public List<PersonalBill> rechnenBill(List<Person> personList, List<Rechnung> rechnungList){
        List<PersonalBill> money = init(personList);
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        for (Rechnung r: rechnungList) {
            BigDecimal zahl = r.getMoney().divide(new BigDecimal(r.getPersons().size()),mc);
            for (PersonalBill m:money) {
                if(m.getPerson().equals(r.getPayer())){
                    BigDecimal geld = m.getGeld();
                    m.setGeld((r.getMoney().add(geld).setScale(2,RoundingMode.HALF_UP)));
                }
                if(r.getPersons().contains(m.getPerson())){
                    m.setGeld((m.getGeld().subtract(zahl).setScale(2,RoundingMode.HALF_UP)));
                }
            }
        }
        return money;
    }

    public List<Result> splitter(List<PersonalBill> personalBillList){
        List<PersonalBill> receiver= personalBillList.stream().filter(e -> e.getGeld().signum()==1).toList();
        List<PersonalBill> giver = personalBillList.stream().filter(e -> e.getGeld().signum()==-1).toList();
        List<Result> results = new ArrayList<>();
        for (PersonalBill personalBill : receiver) {
            for (PersonalBill bill : giver) {
                if (personalBill.getGeld().compareTo(bill.getGeld().abs())>-1) {
                    if(bill.getGeld().abs().signum()==1) {
                        results.add(new Result(personalBill.getPerson(), bill.getPerson(), bill.getGeld().abs()));
                    }
                    personalBill.setGeld(personalBill.getGeld().add(bill.getGeld()));
                    bill.setGeld(new BigDecimal(0));
                } else {
                    if(personalBill.getGeld().abs().signum()==1){
                        results.add(new Result(personalBill.getPerson(), bill.getPerson(), personalBill.getGeld().abs()));
                    }
                    bill.setGeld(bill.getGeld().add(personalBill.getGeld()));
                    personalBill.setGeld(new BigDecimal(0));
                }
            }
        }
        return results;
    }

    public List<Result> result(List<Person> personList,List<Rechnung> rechnungList){
        return splitter(rechnenBill(personList,rechnungList));
    }



}



