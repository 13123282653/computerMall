package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ICartService;
import com.cy.store.service.IOrderService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    IAddressService addressService;//依赖于address业务层的getByAid方法
    @Autowired
    ICartService cartService;//依赖于cart业务层的getByCid方法
    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        //获取到即将要下单的列表
        List<CartVO> list = cartService.getVOByCids(uid, cids);
        //计算商品的总价
        Long totalPrice = 0L;
        for (CartVO c : list) {
            totalPrice += c.getRealPrice() * c.getNum();//一个商品的价格 c.getRealPrice()*c.getNum()

        }

        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();

        // 补全数据：订单的用户的uid
        order.setUid(uid);

        // 补全数据：收货地址相关的6项
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());

        // 补全数据：totalPrice
        order.setTotalPrice(totalPrice);
        // 补全数据：支付状态status——0-未支付，1-已支付，2-已取消，3-已关闭，4-已完成'
        order.setStatus(0);
        // 补全数据：下单时间
        order.setOrderTime(new Date());
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());
        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("插入订单数据时出现未知错误");
        }

        //创建订单项的详细数据——现有大框架的订单数据，才能获取到订单数据里的订单项的数据
        // 遍历carts，循环插入订单商品数据
        for (CartVO cart : list) {
            // 创建订单商品数据对象
            OrderItem item = new OrderItem();
            // 补全数据：setOid(order.getOid())
            item.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            item.setPid(cart.getPid());
            item.setTitle(cart.getTitle());
            item.setImage(cart.getImage());
            item.setPrice(cart.getRealPrice());
            item.setNum(cart.getNum());
            // 补全数据：4项日志
            item.setCreatedUser(username);
            item.setCreatedTime(new Date());
            item.setModifiedUser(username);
            item.setModifiedTime(new Date());
            // 插入订单商品数据
            Integer rows2 = orderMapper.insertOrderItem(item);
            if (rows2 != 1) {
                throw new InsertException("插入订单项的商品数据时出现未知错误");
            }
        }
        return order;
    }
}
