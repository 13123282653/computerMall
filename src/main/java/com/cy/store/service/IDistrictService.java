package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 根据父代号来查询区域信息（省/市/区）
     * @param parent 父代号
     * @return 返回多个区域的信息
     */
    List<District>getParent(String parent);

    /**
     * 根据代号获取省/市/区的名称
     * @param code 代号
     * @return 返回省/市/区的名称
     */
    String getNameByCode(String code);
}
