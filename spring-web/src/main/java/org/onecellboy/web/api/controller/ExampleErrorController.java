package org.onecellboy.web.api.controller;

import org.onecellboy.web.api.response.error.ApiError;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@RestController
public class ExampleErrorController  implements ErrorController {
    @RequestMapping(value="/error", produces = {"application/xml"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public Object handleError(HttpServletRequest request, HttpServletResponse respons, Model model) {
        //do something like logging
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuffer requestURL = request.getRequestURL();
        int status = respons.getStatus();
        Enumeration<String> attributeNames = request.getAttributeNames();
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        Object attribute = request.getAttribute("javax.servlet.error.message");
        Object request_uri = request.getAttribute("javax.servlet.error.request_uri");
        return new ApiError();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
