package org.onecell.spring.template.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.istech.controller.AbstractController;
import org.istech.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;


@Slf4j
@RestController
@Validated
@RequestMapping(value = {"/api/v1/test/"})
public class TestController extends  AbstractController {
    @Autowired
    TestService testService;


    @GetMapping(value ="test01" )
    public Object  virtual_tag(
    ) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("name","test");
        hashMap.put("desc","test");
        hashMap.put("quantity",11);
        return hashMap;

    }
}
