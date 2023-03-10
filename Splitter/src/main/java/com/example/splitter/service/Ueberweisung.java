package com.example.splitter.service;

import com.example.splitter.domain.*;
import com.example.splitter.repository.GroupRepo;
import com.example.splitter.repository.PersonRepo;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;


import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class Ueberweisung {
    private GroupRepo groupRepo;
    private PersonRepo personRepo;

    public Ueberweisung(GroupRepo groupRepo,PersonRepo personRepo) {
        this.groupRepo = groupRepo;
        this.personRepo = personRepo;
    }

    public Gruppe findByGroupId(Integer id){
        return groupRepo.findByID(id).orElseThrow();
    }

    public Person findPerson(String name){
        return personRepo.findByName(name);
    }

    public Person creatPerson(String name){
        if(personRepo.exit(name)){
            return findPerson(name);
        }
        else {
            Person person = new Person(name);
            personRepo.save(person);
            return person;
        }

    }

    public Gruppe createGroup(String name,List<Person> personList){
        Random random = new Random();
        Integer integer = random.nextInt(100);
        return groupRepo.create(integer,name,personList);
    }

    public void timeToClose(Integer id,LocalDateTime localDateTime) {
        if ((localDateTime.isAfter(groupRepo.findByID(id).orElseThrow().getLocalDateTime().plusDays(7)))) {
            groupRepo.close(id);
        }
    }

    public List<Gruppe> personGroupeList (Person person) {
        return groupRepo.findbyIDList(person.getGroupIdList());
    }


    public void addRechnung(Integer id , Rechnung rechnung ,LocalDateTime localDateTime) {
        timeToClose(id,localDateTime);
        groupRepo.addRechnung(id,rechnung);
    }

    public Person findPersonByName( Integer id , String name){
        return groupRepo.findByID(id).orElseThrow().getPersonList().stream().filter(e-> e.getName().equals(name)).findFirst().orElseThrow();
    }
    public Rechnung createRechnung(String name,Money money,Person Payer,List<Person> persons){
        return new Rechnung(name,money,Payer,persons);
    }


    public List<Person> createPersonByList(List<String> names){
        List<Person> personList = new ArrayList<>();
        for (String s:names) {
            personList.add(creatPerson(s));
        }
        return personList;
    }

    public void addGruppeId(Integer integer,Person person){
        personRepo.addGruppe(integer,person);
    }




    private List<PersonalBill> money(List<Person> personList){
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
                if(r.getPersons().contains(m.getPerson())){
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

    public List<Result> result(Integer id){
        Gruppe gruppe = groupRepo.findByID(id).orElseThrow();
        List<Person> personList = gruppe.getPersonList();
        List<Rechnung> rechnungList = gruppe.getRechnungList();
        return second(rechnen(personList,rechnungList)).stream().filter(e->!e.getMoney().isZero()).toList();
    }

}
