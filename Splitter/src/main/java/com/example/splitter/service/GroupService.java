package com.example.splitter.service;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Rechnung;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class GroupService {
    private final GroupRepo groupRepo;

    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Gruppe findByGroupId(Integer id){
        return groupRepo.findByID(id);
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
    public void save(Gruppe gruppe){
        groupRepo.save(gruppe);
    }

    public boolean exist(Integer id){
        return !groupRepo.findAll().stream().filter(gruppe -> gruppe.getId().equals(id)).toList().isEmpty();
    }
    public void create(String name,List<String> personen){
        groupRepo.save(new Gruppe(name, personen));
    }

    public List<Gruppe> findAll(){
        return groupRepo.findAll();
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
            gruppe.setGeschlossen(true);
            groupRepo.save(gruppe);
        }

    }
}
