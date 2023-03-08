package com.example.splitter.repository;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupRepo {
    private List<Gruppe> gruppeList = new ArrayList<>();

    public List<Gruppe> findAll(){
        return gruppeList;
    }
    public Optional<Gruppe> findByID(Integer id){
        return gruppeList.stream().filter(gruppe -> gruppe.getId()==id).findAny();
    }


    public void addNewGroup(Integer id, LocalDateTime localDateTime,Person person){
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        gruppeList.add(new Gruppe(id,personList,new ArrayList<>(),true,localDateTime));
        person.getGroupIdList().add(id);
    }

    public List<Gruppe> findbyIDList (List<Integer> idList){
        List<Gruppe> g = new ArrayList<>();
        for ( Integer id:  idList) {
            g.add(findByID(id).orElseThrow());
        }
        return  g;
    }
    public boolean contains(Integer id ,Person person){
        return findByID(id).get().getPersonList().stream().
                filter(person1 -> person1.getName().equals(person.getName())).toList().isEmpty();
    }

    public void addPerson(Integer id,Person person){
        Gruppe gruppe = findByID(id).orElseThrow();
        if(gruppe.getRechnungList().isEmpty() && gruppe.getOpenStatus().equals(true) && contains(id,person)){
            gruppe.getPersonList().add(person);
            person.getGroupIdList().add(id);
        }

    }

    public void addRechnung(Integer id, Rechnung rechnung){
        Gruppe gruppe = findByID(id).orElseThrow();
        if(gruppe.getOpenStatus().equals(true)){
            gruppe.getRechnungList().add(rechnung);
        }
    }

    public void close(Integer id){
        Gruppe gruppe = findByID(id).orElseThrow();
        gruppe.setOpenStatus();
    }






}
