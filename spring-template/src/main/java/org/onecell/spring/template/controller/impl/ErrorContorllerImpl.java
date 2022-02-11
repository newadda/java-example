package org.onecell.spring.template.controller.impl;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 404 에러등 처리되지 않는 에러에 대해 가장 마지막에 처리하여 응답하는 Controller 이다.
*
* 반환이 null 이면 다른 처리로 넘긴다.
*
* 정리 : 모든 처리되지 않은 에러를 Custom 할 수 있는 가장 마지막 Handler 이다.
*
* */


@Controller
public class ErrorContorllerImpl implements ErrorController {
    @RequestMapping(value="/error")
    @ResponseBody
    public Object handleError(HttpServletRequest request, HttpServletResponse response) {
        //do something like logging
        int status = response.getStatus();

        System.out.println(status);  //오류 상태
        System.out.println(request.getRequestURI());  //요청 주소
        return "error";
    }



}
