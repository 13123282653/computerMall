package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

//收货地址的持久层的接口
@Mapper
public interface AddressMapper {
    /**
     * 插入用户的收货地址数据
     * @param address 收货地址数据
     * @return 返回受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址的数量
     * @param uid 用户id
     * @return 返回用户的收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的id查询用户的收货地址的数据
     * @param uid 用户id
     * @return 返回收货地址的数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     * @param aid 收货地址id
     * @return 返回收货地址数据，如果没有找到返回null
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户的id来修改用户的收货地址设置为非默认
     * @param uid 用户的id
     * @return 返回受影响行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据地址的id，将用户当前选中的这条记录设置为默认地址
     * @param aid
     * @return
     */
    Integer updateDefaultByAid(Integer aid, @Param("modifiedUser") String modifiedUser,
                               @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据收货地址的id删除收货地址数据
     * @param aid 地址的id
     * @return 返回受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户uid来查询当前用户最后一次被修改的收货地址的数据
     * @param uid 用户的id
     * @return 返回收货地址的数据
     */
    Address findLastModified(Integer uid);
}
