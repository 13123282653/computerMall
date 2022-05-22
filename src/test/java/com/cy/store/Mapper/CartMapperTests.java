package com.cy.store.Mapper;

import com.cy.store.entity.Cart;
import com.cy.store.mapper.CartMapper;
import com.cy.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {
    @Autowired
    CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setUid(8);
        cart.setPid(10000030);
        cart.setNum(2);
        cart.setPrice(1000L);//Long类型后面要有L来进行标识
        System.out.println(cartMapper.insert(cart));
    }
    @Test
    public void updateNum(){
        System.out.println(cartMapper.updateNum(1, 3, "管理员", new Date()));
    }

    @Test
    public void findByUidAndPid(){
        System.out.println(cartMapper.findByUidAndPid(8, 10000030));
    }

    @Test
    public void findVOByUid(){
        List<CartVO> list = cartMapper.findVOByUid(8);
        System.out.println(list);
    }

    @Test
    public void findByCid(){
        System.out.println(cartMapper.findByCid(5));
    }

    @Test
    public void findVOByCids() {
        Integer[] cids = {1, 2, 5, 6, 7};
        List<CartVO> list = cartMapper.findVOByCids(cids);
        for (CartVO item : list) {
            System.out.println(item);
        }
    }

}
