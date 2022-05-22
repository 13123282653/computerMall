package com.cy.store.controller;

import com.cy.store.entity.District;
import com.cy.store.service.impl.DistrictServiceImpl;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController extends  BaseController{
    @Autowired
    DistrictServiceImpl districtService;

    //凡是以districts开头的请求都会被拦截到getByParent方法，但是我们也必须要在这个方法上写@RequestMapping
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = districtService.getParent(parent);
        return new JsonResult<List<District>>(OK,data);
    }


}
