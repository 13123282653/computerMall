package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CartMapper {

    /**
     * 插入购物车的数据
     *
     * @param cart 购物车数据
     * @return 返回受影响行数
     */
    Integer insert(Cart cart);


    /**
     * 更新购物车某件商品的数量
     *
     * @param cid          购物车数据id
     * @param num          更新的数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 返回受影响行数
     */
    Integer updateNum(Integer cid, Integer num, String modifiedUser, Date modifiedTime);


    /**
     * 根据用户的id和商品的id来查询购物车的数据
     *
     * @param uid 用户id
     * @param pid 商品id
     * @return 返回购物车数据
     */
    Cart findByUidAndPid(Integer uid, Integer pid);

    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    List<CartVO> findVOByUid(Integer uid);


    /**
     * 根据购物车数据id查询购物车数据详情
     * @param cid 购物车数据id
     * @return 匹配的购物车数据详情，如果没有匹配的数据则返回null
     */
    Cart findByCid(Integer cid);

    /**
     * 根据若干个购物车数据id查询详情的列表
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVO> findVOByCids(Integer[] cids);






}
