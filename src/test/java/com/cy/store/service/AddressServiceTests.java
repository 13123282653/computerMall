package com.cy.store.service;

import com.cy.store.entity.Address;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired
    IAddressService addressService;
    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("17858805555");
        address.setName("张三");
        addressService.addNewAddress(12,"测试",address);
    }
    @Test
    public void getByUid(){
        List<Address> list = addressService.getByUid(8);
        System.out.println(list);
    }

    @Test
    public void setDefault(){
        addressService.setDefault(10,8,"管理员");
    }

    @Test
    public void delete(){
        addressService.delete(9,8,"管理员");
    }
}
