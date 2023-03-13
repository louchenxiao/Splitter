package com.example.splitter.repository;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.service.GroupRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupRepoImpl implements GroupRepo {
    private List<Gruppe> gruppeList = new ArrayList<>();

    @Override
    public List<Gruppe> findAll(){
        return gruppeList;
    }
    @Override
    public Optional<Gruppe> findByID(Integer id){
        return gruppeList.stream().filter(gruppe -> gruppe.getId()==id).findAny();
    }

    @Override
    public void save(Gruppe gruppe) {
        gruppeList.add(gruppe);
    }
}
