package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.PrinterURI;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    //购物车的业务层依赖于购物车的持久层以及商品的持久层
    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;
    @Override
    public void addToCart(Integer uid, Integer pid, Integer num, String username) {
        //查询当前要添加的这个商品是否是已经存在的
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if(result==null){//表示这个商品从来没有被添加到购物车中，则进行插入操作
            //需要创建cart对象
            Cart cart = new Cart();
            //因为新创建的对象是空的，所以进行补全数据，先补全形参的
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(num);
            //价格来源于商品的数据
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());
            //补全4个日志
            cart.setCreatedUser(username);
            cart.setModifiedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedTime(new Date());
            //执数据的插入操作
            Integer rows = cartMapper.insert(cart);
            if(rows!=1){
                throw new InsertException("插入时产生未知的异常");
            }
        } else {//表示当前的商品在购物车中已经存在，则只需要更新num的值即可
            Integer nums = result.getNum() + num;//总数等于购物车里的数加上我们要添加的数量
            Integer rows = cartMapper.updateNum(result.getCid(), nums, username, new Date());
            if (rows != 1) {
                throw new UpdateException("更新时产生未知的异常");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        List<CartVO> list = cartMapper.findVOByUid(uid);
        return list;
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if(result==null){
            throw new CartNotFoundException("购物车数据不存在");
        }
        if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        //对商品数量做+1的操作
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNum(cid, num, username, new Date());
        if(rows!=1){
            throw new UpdateException("更新数据产生未知的异常");
        }
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        for(CartVO cart :list){
            if(!cart.getUid().equals(uid)){
                list.remove(cart);
            }
        }
        return list;
    }
}
