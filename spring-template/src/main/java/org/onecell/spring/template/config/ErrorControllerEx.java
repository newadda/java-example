package org.onecell.spring.template.config;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

/*
- Exception 등 에러의 마지막이다. 어떤 에러든 처리되지 않으면 최종적으로 이곳에서 처리된다.
- 특히 Not Found 에러는 이곳에서 처리해야 한다. Not Found Page는 Exception이 아니고 그냥 처리되는 에러이기 때문에 이곳에서만 처리된다.

중요 : request.getAttribute() 즉 request 의 attribute 에 에러 내용을 담고 있다. exception을 던지기 전에 여기에 정보를 넣어서 던지면 사용할 수 있다.
보통 Rest 요청(Contents-Type : application/json) 일때는 에러가 아래가 기본이다.
{
    "timestamp": 1545167796302,
    "status": 401,
    "error": "Unauthorized",
    "message": "Unauthorized",
    "path": "/oauth/token"
}


보면 알겠지만 에러의 상태나 에러가 발생한 이유 등은 request.getAttribute() 에 존재한다.

아래는 getAttribute()에서 사용할 이름의 예제이다.
0 = "org.springframework.web.context.request.async.WebAsyncManager.WEB_ASYNC_MANAGER"
1 = "javax.servlet.error.status_code"
2 = "org.springframework.web.servlet.DispatcherServlet.CONTEXT"
3 = "__spring_security_scpf_applied"
4 = "org.springframework.web.servlet.resource.ResourceUrlProvider"
5 = "__spring_security_session_mgmt_filter_applied"
6 = "__spring_security_filterSecurityInterceptor_filterApplied"
7 = "org.springframework.security.web.FilterChainProxy.APPLIED"
8 = "javax.servlet.error.servlet_name"
9 = "javax.servlet.error.message"
10 = "org.springframework.web.servlet.DispatcherServlet.THEME_SOURCE"
11 = "org.springframework.web.servlet.HandlerMapping.producibleMediaTypes"
12 = "org.springframework.web.servlet.DispatcherServlet.LOCALE_RESOLVER"
13 = "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern"
14 = "org.springframework.web.servlet.DispatcherServlet.OUTPUT_FLASH_MAP"
15 = "org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"
16 = "org.springframework.web.servlet.DispatcherServlet.FLASH_MAP_MANAGER"
17 = "javax.servlet.error.request_uri"
18 = "org.springframework.web.servlet.HandlerMapping.uriTemplateVariables"
19 = "org.springframework.web.servlet.DispatcherServlet.THEME_RESOLVER"
20 = "org.springframework.core.convert.ConversionService"


 자세한 구현은 org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController 를 보는 것이 좋다.
 이게 기본적인 Spring 처리 방법이다.

 */
//@Controller
public class ErrorControllerEx  implements ErrorController {


    @RequestMapping(value="/error", produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String handleError(HttpServletRequest request, HttpServletResponse respons, Model model) {
        //do something like logging
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuffer requestURL = request.getRequestURL();
        int status = respons.getStatus();
        Enumeration<String> attributeNames = request.getAttributeNames();
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        Object attribute = request.getAttribute("javax.servlet.error.message");
        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}

