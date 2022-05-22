package com.cy.store.service;

import com.cy.store.service.ex.ServiceException;
import com.cy.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceTests {
    @Autowired
    ICartService cartService;
    @Test
    public void addToCart(){
        cartService.addToCart(8,10000030,3,"管理员");
    }

    @Test
    public void getVOByUid() {
        List<CartVO> list = cartService.getVOByUid(8);
        System.out.println("count=" + list.size());
        for (CartVO item : list) {
            System.out.println(item);
        }
    }
    @Test
    public void addNum() {
        Integer cid = 6;
        Integer uid = 8;
        String username = "管理员";
        Integer num = cartService.addNum(cid, uid, username);
        System.out.println("更新后的num="+num);
    }

    @Test
    public void getVOByCids() {
        Integer[] cids = {1, 2, 5, 6, 7};
        Integer uid = 8;
        List<CartVO> list = cartService.getVOByCids(uid, cids);
        for (CartVO item : list) {
            System.out.println(item);
        }
    }


}
