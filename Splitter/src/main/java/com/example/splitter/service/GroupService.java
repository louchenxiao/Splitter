package com.example.splitter.service;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.repository.GroupRepo;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class GroupService {
    private GroupRepo groupRepo;

    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Gruppe findByGroupId(Integer id){
        return groupRepo.findByID(id).orElseThrow();
    }

    public Gruppe create(String name,List<Person> personen){
        Random random = new Random();
        Gruppe gruppe = new Gruppe(random.nextInt(100), name, personen);
        groupRepo.save(gruppe);
        return gruppe;
    }


    public void addRechnung(Integer id,Rechnung rechnung){
        if (!findByGroupId(id).getGeschlossen()) {
            findByGroupId(id).getRechnungList().add(rechnung);
        }
    }

    public List<Gruppe> getAllGruppe(List<Integer> idList){
        List<Gruppe> gruppeList = new ArrayList<>();
        for (Integer i:idList) {
            gruppeList.add(findByGroupId(i));
        }
        return gruppeList;
    }

    public void closeGruppe(Integer id){
        Gruppe gruppe = groupRepo.findByID(id).orElseThrow();
        gruppe.setGeschlossen();
    }
}
