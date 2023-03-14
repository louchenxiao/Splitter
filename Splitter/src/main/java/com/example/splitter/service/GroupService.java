package com.example.splitter.service;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GroupService {
    private final GroupRepo groupRepo;

    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Gruppe findByGroupId(Integer id){
        return groupRepo.findByID(id).orElse(null);
    }

    public Integer check(String s){
        if(exist(toInteger(s))){
            return toInteger(s);
        }
        else return -1;
    }

    public Integer toInteger(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    public boolean exist(Integer id){
        return !groupRepo.findAll().stream().filter(gruppe -> gruppe.getId().equals(id)).toList().isEmpty();
    }
    public Gruppe create(String name,List<Person> personen){
        Gruppe gruppe = new Gruppe(findAll().size()+1, name, personen);
        groupRepo.save(gruppe);
        return gruppe;
    }

    public List<Gruppe> findAll(){
        return groupRepo.findAll();
    }

    public boolean getStatus (Integer id) {
        return findByGroupId(id).getGeschlossen();
    }

    public void addRechnung(Integer id,Rechnung rechnung){
        if (exist(id) && !findByGroupId(id).getGeschlossen() ) {
            findByGroupId(id).getRechnungList().add(rechnung);
        }
    }

    public List<Gruppe> getAllGruppe(List<Integer> idList){
        List<Gruppe> gruppeList = new ArrayList<>();
        for (Integer i:idList) {
            if (exist(i)) {
                gruppeList.add(findByGroupId(i));
            }
        }
        return gruppeList;
    }

    public void closeGruppe(Integer id) {
        if (exist(id)) {
            Gruppe gruppe = findByGroupId(id);
            gruppe.setGeschlossen();
        }
    }
}
