package org.onecellboy.web.mvc.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@Component
public class RestResponseStatusExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        response.setStatus(400);
        ModelAndView modelAndView = new ModelAndView();

        return  null;
    }



}
