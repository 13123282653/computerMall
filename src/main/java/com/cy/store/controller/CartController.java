package com.cy.store.controller;

import com.cy.store.service.ICartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{

    @Autowired
    ICartService cartService;

    @RequestMapping("/add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer num, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(uid,pid,num,username);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"/",""})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<CartVO> data = cartService.getVOByUid(uid);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer num = cartService.addNum(cid, uid, username);
        return new JsonResult<>(OK,num);
    }
    @GetMapping("list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session){
        // 从Session中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行查询数据
        List<CartVO> data = cartService.getVOByCids(uid, cids);
        // 返回成功与数据
        return new JsonResult<>(OK, data);
    }

}
