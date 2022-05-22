package com.cy.store.service;

import com.cy.store.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTests {
    @Autowired
    IOrderService orderService;

    @Test
    public void create(){
        Order order = orderService.create(15, new Integer[]{5, 6, 7}, 8, "管理员");
        System.out.println(order);
    }
}
