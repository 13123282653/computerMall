package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;

import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
//新增收货地址的实现类
public class AddressServiceImpl implements IAddressService {
    @Autowired
    AddressMapper addressMapper;

    @Value("${users.address.max-count}")
    private Integer maxCount;

    //添加用户的收货地址的业务层要依赖于IDistrictService的业务层接口
    @Autowired
    IDistrictService districtService;

    @Override
    public void addNewAddress(Integer uid, String username,Address address) {
        //调用收货地址统计的方法
        Integer count = addressMapper.countByUid(uid);
        if(count>=maxCount){
            throw new AddressCountLimitException("收货地址超出上限");
        }


        //对address对象的数据进行补全：省、市、区的数据
        //address.getProvinceCode()是怎么获取的呢？——通过前端的按钮的列表获取到的，获取到之后将这些值设置在address对象里
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);


        //获取uid和isDelete
        address.setUid(uid);
        //1表示默认的地址，0表示不是默认的
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        //补全4项日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //调用插入收货地址的方法
        Integer rows = addressMapper.insert(address);
        if(rows!=1){
            throw new InsertException("插入用户的收货地址产生未知的异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        //地址展示的信息只要有：地址类型(Tag)、收货人姓名(name)、详细地址、联系电话(phone)
        for(Address address:list){
            // address.setAid(null);
            // address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("收货地址不存在");
        }
        //检测当前获取到的收货地址数据的归属
        if(!result.getUid().equals(uid)){
            throw  new AccessDeniedException("非法访问数据");
        }

        //先将所有的收货地址设置为非默认地址
        Integer rows = addressMapper.updateNonDefault(uid);
        if(rows==0){
            throw new UpdateException("更新数据产生未知的异常");
        }

        //将用户选中的某条地址设置为默认地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }

        Integer rows = addressMapper.deleteByAid(aid);
        if(rows!=1){
            throw new DeleteException("删除数据产生未知的异常");
        }
        Integer count = addressMapper.countByUid(uid);
        if(count==0){
            //如果用户本身只有一条收货地址的数据，那么删除后，其他的操作就可以不用进行。
            return ;
        }

        //如果用户有多条地址，并且删除的这一条地址不是默认地址，那么就结束操作
        if(result.getIsDefault()==0){
            return ;
        }

        //如果要删除的这条地址是默认地址的话，那么就要再重新设置默认地址
        Address address = addressMapper.findLastModified(uid);
        //将这条数据的is_default设置为1
        //这里的aid不能是形参的aid,形参的aid是要删除的数据的aid
        rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新时产生未知的异常");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }
}
