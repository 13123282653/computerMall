package com.cy.store.service;

import com.cy.store.entity.District;
import com.cy.store.service.impl.DistrictServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {
    @Autowired
    DistrictServiceImpl districtService;

    @Test
    public void getParent(){
        //86表示中国，所有省的父代号都是86
        List<District> list = districtService.getParent("86");
        for(District d:list){
            System.out.println(d);
        }
    }

    @Test
    public void getNameByCode(){
        System.out.println(districtService.getNameByCode("610000"));
    }
}
