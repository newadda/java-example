package org.onecell.spring.template.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.istech.controller.AbstractController;
import org.istech.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;


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

    @Validated
    @PreAuthorize("isAuthenticated() and hasAnyRole('ADMIN','VISITOR')")
    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    @ResponseBody
    public Object test(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {

        DatasDto<TbExpOrganInfoDto> dto = broadcastService.listExpOrganInfo();

        return dto;
    }

    /*
        /// 비상연락처 목록
    @Validated
    @PreAuthorize(ConstantProp.VISITOR_PREAUTHORIZE)
    @RequestMapping(value = {"/sms_addrs"}, method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Object listSmsAddr(HttpServletRequest request, HttpServletResponse response, Locale locale,
                              ListSmsAddrReq req
    ) {
        return smsService.listSmsAddr(req);
    }

    /// 비상연락처 생성 및 수정
    @Validated
    @PreAuthorize(ConstantProp.ADMIN_PREAUTHORIZE)
    @RequestMapping(value = {"/sms_addrs/create_or_update"}, method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Object createSmsAddr(HttpServletRequest request, HttpServletResponse response, Locale locale,
                                @Validated @RequestBody CreateOrUpdateSmsAddrReq dto) {
        if (dto.getSms_addr_seq() == null) {
            return smsService.createSmsAddr(dto);
        } else {
            return smsService.updateSmsAddr(dto);
        }
    }

     */
}
