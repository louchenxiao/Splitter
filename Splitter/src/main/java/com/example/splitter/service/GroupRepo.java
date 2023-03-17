package com.example.splitter.service;

import com.example.splitter.domain.Gruppe;

import java.util.List;
import java.util.Optional;

public interface GroupRepo {
    List<Gruppe> findAll();

    Gruppe findByID(Integer id);

    void save(Gruppe gruppe);
}
