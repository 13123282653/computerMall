package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

//用户模块业务层的实现类
@Service//@Service注解：将当前类的对象交给spring来管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {
    @Autowired
    //实现类的任务是调用mapper层的方法，再把user对象传递下去
    private UserMapper userMapper;

    /**
     * 并不是一上来就调用方法，而是先判断异常处理——先检查用户名是否被占用了
     * @param user 用户的数据对象
     */
    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        //调用User findByUsername(String username); 来判断用户是否被注册
        User result = userMapper.findByUsername(username);
        //如果result不为null，则抛出用户名被占用的异常
        if(result!=null){
            throw new UsernameDuplicateException("用户名被占用");
        }

        /*补全数据要在注册用户前完成*/

        //密码加密处理的实现：md5算法(目前较为流行)
        //加密方式：串+password+串----->md5算法进行加密
        //盐值+password+盐值----->盐值是一个随机的字符串
        String oldPassword = user.getPassword();
        //获取全大写的盐值(利用uuid获取随机不重复的值)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据：盐值的记录
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密处理（因为md5算法往后可能还会用到，所以把它写成一个方法进行调用）
        String md5Password=getMD5Password(oldPassword,salt);
        //将加密之后的密码覆盖之前的旧密码
        user.setPassword(md5Password);

        //补全数据：isDelete设置成0
        user.setIsDelete(0);
        //补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setModifiedUser(username);
        //创建当前时间对象
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现(当rows==1时操作成功)
        Integer rows = userMapper.insert(user);
        if(rows!=1){
            throw new InsertException("用户在注册的过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {
        //根据用户名称来查询用户的数据是否存在，如果不存在则抛出异常
        User result = userMapper.findByUsername(username);
        if(result==null){
            throw new UserNotFoundException("用户名不存在");
        }
        //检测用户的密码是否匹配
        //1.先获取到数据库加密的密码
        String oldPassword=result.getPassword();
        //2.和用户的传递过来的密码进行比较
        //2.1先获取盐值：盐值是上一次在注册时所自动生成的盐值
        String salt=result.getSalt();
        //2.1将用户的密码按照相同的md5算法的规则进行加密
        String newMd5Password=getMD5Password(password,salt);
        //3.将密码进行比较
        if(!oldPassword.equals(newMd5Password)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断is_Delete字段的值是否为1，为1表示被标记为删除
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }

        //调用mapper层的findByUsername来查询用户的数据,在开头为了根据用户名称来查询用户的数据是否存在而调用过了
        //将当前的用户数据返回,返回一些必要的数据就行了。而不必将所有数据都返回，这样子提高了系统的性能，数据量越小，速度越快
        //返回的数据(uid,username,avatar)是为了辅助其他页面做数据展示使用
        User user=new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        //返回有用户的头像
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }

        //原始密码和数据库的密码进行比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if(!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新的密码设置到数据库中,将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if(rows!=1){
            throw  new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        //查询数据是否存在，如果不存在抛异常，存在就将需要的数据封装并返回
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //result要展示给前端页面,虽然直接return result是没有问题的,
        //但是这样子封装数据的话，数据太多了，还是建议手动创建一个过渡的user对象,将需要的数据封装的user对象中再返回
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    /**
     * 底层是根据用户的uid来做数据更新的，而用户对象的数据只有三部分：phone\email\gender
     * @param uid 用户id
     * @param username 用户名
     * @param user 用户对象的数据(只有三部分 phone\email\gender)
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        //user.setUsername(username);前端的表单有这个数据就不需要在设置了
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if(rows!=1){
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询当前用户数据是否存在
        User result = userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if(rows!=1){
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    /**
     *
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    //定义md5算法的加密处理
    private String getMD5Password(String password, String salt){
        /**
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            //MD5加密算法方法调用
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
