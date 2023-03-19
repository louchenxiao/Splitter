package com.example.splitter.service;

import com.example.splitter.domain.Gruppe;

import java.util.List;


public interface GroupRepo {
    List<Gruppe> findAll();

    Gruppe findByID(Integer id);

    void save(Gruppe gruppe);
}
