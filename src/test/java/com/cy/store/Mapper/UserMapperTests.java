package com.cy.store.Mapper;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest//表示标注当前的类是一个测试类，特点：不会随同项目打包发送
@RunWith(SpringRunner.class)//表示启动这个单元测试类(如果没有这个注解，单元测试类是不能运行的)，需要传递一个参数：SpringRunner.class(固定)
public class UserMapperTests {
    /**
     * 单元测试类的特点
     * 满足如下4点，就可以单独独立运行，不用启动整个项目，可以做单元测试，提升了代码的效率
     * 1.必须被@Test注解修饰
     * 2.返回值必须是void
     * 3.方法的形参列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */

    /**
     * 1.要测试接口userMapper对应映射文件所对应的方法，就要自动装配（@autowired） UserMapper userMapper
     * 2.idea有检测的功能，接口是不能直接创建bean的，所以认为你这个语法是不合理的，会报错。
     * 但是本质上这个项目在启动时，mybatis帮我们创建了这个接口的动态代理来解决这个错误。
     * 3.将报错的红线弄掉（不弄掉也可以运行成功）如下操作：
     * 设置(settings)——编辑(editor)——检查(inspections)——Spring Core——core——Autowiring for bean class(Spring Bean组件中不正确的注入点自动装配)
     * 将Spring Bean组件中不正确的注入点自动装配的严重程度改为警告
     */
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user=new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println("rows="+rows);
    }

    @Test
    public void findByUsername() {
        String username = "tim";
        User result = userMapper.findByUsername(username);
        System.out.println(result);
    }
    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(6,"123","管理员",new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(6));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(8);
        user.setPhone("13131313131");
        user.setEmail("13131313131@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(8,"/upload/avator.png","管理员",new Date());
    }

}
