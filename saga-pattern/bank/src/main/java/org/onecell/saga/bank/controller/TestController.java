package org.onecell.saga.bank.controller;

import lombok.extern.slf4j.Slf4j;
import org.onecell.saga.bank.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
@Validated
@RequestMapping(value = {"/test"})
public class TestController {

    @Autowired
    private TestService testService;

    @Validated
    //@PreAuthorize()
    @RequestMapping(value = {"/createAccount"}, method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Object createAccount(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {
         testService.createAccount();
        return null;
    }
}
