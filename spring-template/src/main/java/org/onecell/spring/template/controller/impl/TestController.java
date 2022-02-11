package org.onecell.spring.template.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.onecell.spring.template.controller.AbstractController;
import org.onecell.spring.template.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;

/*
* 응답 객체
* String: @ResponseBody 가 없을 경우 view 이름
* void : 컨트롤러에서 응답을 직접 처리
* ModelAndView : 모델과 뷰 정보를 함께 반환
* Object : 메소드에 @ResponseBody가 적용된 경우, 리턴 객체를 Json, Xml 과 같은 알맞은 응답으로 변환
* ResponseEntity : http 응답을 빠르게 만들어주기 위한 객체.
* */

@Slf4j
@RestController
@Validated
@RequestMapping(value = {"/api/v1/test/"})
public class TestController extends AbstractController {
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
    //@PreAuthorize("isAuthenticated() and hasAnyRole('ADMIN','VISITOR')")
    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    @ResponseBody
    public Object test(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {

        testService.findAll();

        return null;
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
