package org.onecell.saga.bank.controller;

import lombok.extern.slf4j.Slf4j;
import org.onecell.saga.bank.dto.DepositReq;
import org.onecell.saga.bank.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Validated
    //@PreAuthorize()
    @RequestMapping(value = {"/deposit"}, method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Object createAccount(HttpServletRequest request, HttpServletResponse response, Locale locale,
                                @RequestBody DepositReq req
    ) {
        testService.deposit(req);
        return null;
    }
}
