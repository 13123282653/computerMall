package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DistrictMapper {
    /**
     * 根据用户的父代号查询区域信息
     * @param parent 父代号
     * @return 返回某一个父区域下的所有区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据省市区的代号获取省市区的名称
     * @param code 省区市的代号
     * @return 返回省市区的名称
     */
    String finaNameByCode(String code);
}
