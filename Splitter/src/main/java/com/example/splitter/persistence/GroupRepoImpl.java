package com.example.splitter.persistence;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.service.GroupRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepoImpl implements GroupRepo {
    private final SpringdataGroupRepo springdataGroupRepo;

    public GroupRepoImpl(SpringdataGroupRepo springdataGroupRepo) {
        this.springdataGroupRepo = springdataGroupRepo;
    }

    @Override
    public List<Gruppe> findAll(){
        return springdataGroupRepo.findAll();
    }
    @Override
    public Gruppe findByID(Integer id){
        return springdataGroupRepo.findAll().stream().filter(gruppe -> gruppe.getId().equals(id)).findAny().get();
    }

    @Override
    public void save(Gruppe gruppe) {
        springdataGroupRepo.save(gruppe);
    }
}
