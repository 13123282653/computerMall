package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicateException;
import com.cy.store.util.JsonResult;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// @Controller
@RestController//@Controller和@ResponseBody的结合
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired//控制层对应的类，依赖于业务层的接口
    private IUserService userService;

    @RequestMapping("reg")
    // @ResponseBody//表示此方法的响应结果以json格式进行数据的响应并给到前端
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User>login(String username,String password,HttpSession session){
        User data = userService.login(username, password);
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        System.out.println("Session中的uid=" + getUidFromSession(session));
        System.out.println("Session中的username=" + getUsernameFromSession(session));

        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_id")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void>changeInfo(User user,HttpSession session){
        //user对象中有四部分数据: username、phone、email、gender
        //uid数据需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }

    //设置上传文件的最大值为10MB
    public static final  int AVATAR_MAX_SIZE=10*1024*1024;
    //限制上传文件的类型
    public static final List<String>AVATAR_TYPE=new ArrayList<>();
    static{
        AVATAR_TYPE.add("image/jpeg");//jpeg包含了jpg
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    @RequestMapping("change_avatar")
    public JsonResult<String>changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file){
        //判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        //判断文件的大小是否超出限制
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        //判断文件的类型是否是我们规定的后缀类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        //文件夹上传的文件的路径：.../upload/文件.png
        //session.getServletContext()获取的上下文路径
        //getRealPath()真实路径
        // String parent = session.getServletContext().getRealPath("upload");
        String parent="D:/store/upload";//换成固定路径
        //file对象指向这个路径，file是否存在
        File dir = new File(parent);
        if(!dir.exists()){
            dir.mkdirs();//创建目录
        }
        //获取到这个文件的名称,UUID工具来将生产一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件的后缀名 eg: .png
        int indexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(indexOf);
        //前缀名:UUID.randomUUID().toString().toUpperCase()  +后缀名:suffix
        String filename = UUID.randomUUID().toString().toUpperCase()+suffix;

        /**
         * 通过给定的父抽象路径名和子路径名字符串创建一个新的File实例。
         * File(File parent, String child);
         */
        //创建这个文件放在dir目录里，在这个目录里存放filename的文件
        //即在dir目录下创建一个文件名为filename的文件，这是一个空文件
        File dest=new File(dir,filename);
        //将参数file中的数据写入到这个空文件
        try {
            file.transferTo(dest);//将file文件中的数据写入到dest文件中
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");

        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像的相对路径 : /upload/test.png
        String avatar="/upload"+filename;
        userService.changeAvatar(uid,avatar,username);
        //返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK,avatar);
    }

}
