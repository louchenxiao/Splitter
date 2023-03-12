package com.example.splitter.repository;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GroupRepo {
    private List<Gruppe> gruppeList = new ArrayList<>();

    public List<Gruppe> findAll(){
        return gruppeList;
    }
    public Optional<Gruppe> findByID(Integer id){
        return gruppeList.stream().filter(gruppe -> gruppe.getId()==id).findAny();
    }

    public void save(Gruppe gruppe) {
        gruppeList.add(gruppe);
    }
}
