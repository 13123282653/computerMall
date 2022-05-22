package com.cy.store.Mapper;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    AddressMapper addressMapper;
    @Test
    public void insert(){
        Address address = new Address();
        //只要设置sql语句的一些值就行，不需要全部设置
        address.setUid(8);
        address.setPhone("13131313132");
        address.setName("小叨");
        addressMapper.insert(address);

    }

    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(8);
        System.out.println(count);
    }

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(8);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        Address address = addressMapper.findByAid(9);
        System.out.println(address);
    }

    @Test
    public void updateNonDefault(){
        System.out.println(addressMapper.updateNonDefault(12));
    }

    @Test
    public void updateDefaultByAid(){
        System.out.println(addressMapper.updateDefaultByAid(9,"管理员",new Date()));

    }

    @Test
    public void deleteByAid(){
        System.out.println(addressMapper.deleteByAid(2));
    }

    @Test
    public void findLastModified(){
        System.out.println(addressMapper.findLastModified(8));
    }
}

