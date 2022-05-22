package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

//用户模块的持久层接口
@Mapper
public interface UserMapper {
    /**
     * 插入用户数据
     * @param user 用户的数据
     * @return 受影响的行数(增、删、改、这三个都受影响的行数来作为返回值)
     */
    Integer insert(User user);
    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 如果找到对应的用户，则返回用户的数据，如果没有找到，则返回null
     */
    User findByUsername(String username);

    /**
     * 根据用户的uid来修改用户密码
     * @param uid 用户的id
     * @param password 用户输入的新密码
     * @param modifiedUser 表示修改的执行者
     * @param modifiedTime 表示修改数据的时间
     * @return返回值为受影响的行数
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id查询用户的数据
     * @param uid 用户的id
     * @return 如果找到则返回对象，未找到则返回null
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据信息
     * @param user 用户的数据
     * @return 返回值为受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @param("XXX") :XXX是sql映射文件中#{YYY}占位符的变量名
     * 当sql语句的占位符和映射的接口方法参数名不一致是，需要将某一个参数强行注入到占位符变量是时，可以使用@param这个注解来标识映射关系
     * *根据用户的uid来修改用户的头像
     * @param uid 用户id
     * @param avatar 头像
     * @param modifiedUser 表示修改的执行者
     * @param modifiedTime 表示修改数据的时间
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar")String avatar,
                              @Param("modifiedUser")String modifiedUser,
                              @Param("modifiedTime")Date modifiedTime);
}


