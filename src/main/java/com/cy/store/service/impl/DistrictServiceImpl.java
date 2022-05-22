package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Dictionary;
import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    DistrictMapper districtMapper;
    @Override
    public List<District> getParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        //在进行网络数据传输时，为了尽量避免无效数据的传递，可以将无效数据设置为null，可以节省流量，提升效率
        //我们在通过查询时，只是要为了获得父代码下的code和name其他的不关心，所以设置为null
        for(District d:list){
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        String name = districtMapper.finaNameByCode(code);
        return name;
    }
}
