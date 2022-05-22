package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    //针对IUserService的实现类的测试，所以要调用IUserService对象
    private IUserService UserService;

    @Test
    public void reg(){
        //如果注册失败要抛出异常
        try {
            User user=new User();
            user.setUsername("小军");
            user.setPassword("123");
            UserService.reg(user);
            System.out.println("注册成功");
        } catch (ServiceException e) {
            //获取类的对象，在获取类的名称
            System.out.println(e.getClass());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    //做单元测试的时候，可以不用那么严谨的去考虑异常方面
    public void login(){
        User user = UserService.login("test01", "123456");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        UserService.changePassword(8,"test03","123456","123");
    }

    @Test
    public void getByUid(){
        System.out.println(UserService.getByUid(8));
    }

    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("12121212121");
        user.setEmail("12121212121@qq.com");
        user.setGender(0);
        UserService.changeInfo(8,"管理员",user);
    }
    @Test
    public void changeAvatar(){
        UserService.changeAvatar(8,"/upload/test.png","小明");
    }
}

