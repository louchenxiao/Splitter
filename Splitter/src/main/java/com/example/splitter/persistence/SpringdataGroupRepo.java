package com.example.splitter.persistence;

import com.example.splitter.domain.Gruppe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringdataGroupRepo extends CrudRepository<Gruppe,Integer> {
    List<Gruppe> findAll();

}
