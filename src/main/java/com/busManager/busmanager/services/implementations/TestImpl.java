package com.busManager.busmanager.services.implementations;

import com.busManager.busmanager.repositories.TestRepo;
import com.busManager.busmanager.services.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestImpl implements Test {

    @Autowired
    TestRepo testRepo;
    @Override
    public String testService(String id) {
        return testRepo.create(id);
    }

}
