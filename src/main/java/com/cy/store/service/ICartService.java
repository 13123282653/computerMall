package com.cy.store.service;

import com.cy.store.vo.CartVO;

import java.util.List;

/** 处理商品数据的业务层接口 */
public interface ICartService {
    /**
     * 将商品添加到购物车中
     * @param uid 用户id
     * @param pid 商品id
     * @param num 新增的数量
     * @param username 修改者
     */
    void addToCart(Integer uid,Integer pid,Integer num,String username);

    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    List<CartVO>getVOByUid(Integer uid);

    /**
     * 更新用户的购物车数据的数量
     * @param cid 购物车数量的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 根据若干个购物车数据id查询详情的列表
     * @param uid 当前登录的用户的id
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);

}
