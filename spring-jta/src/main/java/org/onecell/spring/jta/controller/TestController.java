package org.onecell.spring.jta.controller;

import org.onecell.spring.jta.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RestController
@Validated
@RequestMapping(value = {"/testapi"})
public class TestController {
    @Autowired
    TestService testService;

    // 조회 테스트
    @Validated
    @RequestMapping(value = {"/test01"}, method = RequestMethod.GET)
    @ResponseBody
    public Object test01(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {
        Object o = testService.test01();
        return o;
    }

    // 삽입 테스트
    @Validated
    @RequestMapping(value = {"/test02"}, method = RequestMethod.GET)
    @ResponseBody
    public Object test02(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {
        testService.test02();
        return null;
    }
}
