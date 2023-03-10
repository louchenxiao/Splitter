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

    public boolean exist(Integer integer){
        return gruppeList.stream().filter(e->e.getId().equals(integer)).toList().isEmpty();
    }

    public Gruppe create(Integer integer, String name,List<Person> personList){
        Gruppe gruppe = new Gruppe(integer, name, personList);
        gruppeList.add(gruppe);
        return gruppe;
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



    public void addRechnung(Integer id, Rechnung rechnung){
        Gruppe gruppe = findByID(id).orElseThrow();
        if(gruppe.getGeschlossen().equals(false)){
            gruppe.getRechnungList().add(rechnung);
        }
    }

    public void close(Integer id){
        Gruppe gruppe = findByID(id).orElseThrow();
        gruppe.setGeschlossen();
    }






}
