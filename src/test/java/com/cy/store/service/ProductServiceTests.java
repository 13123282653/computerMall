package com.cy.store.service;

import com.cy.store.entity.Product;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {
    @Autowired
    IProductService productService;

    @Test
    public void findHotList() {
        try {
            List<Product> list = productService.findHotList();
            System.out.println("count=" + list.size());
            for (Product item : list) {
                System.out.println(item);
            }
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findById() {
        try {
            Integer id = 10000030;
            Product result = productService.findById(id);
            System.out.println(result);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
