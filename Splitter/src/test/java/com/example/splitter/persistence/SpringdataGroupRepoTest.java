package com.example.splitter.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJdbcTest
class SpringdataGroupRepoTest {

    @Autowired
    SpringdataGroupRepo springdataGroupRepo;

    @Test
    void findAll() {
        springdataGroupRepo.findAll();
    }
}