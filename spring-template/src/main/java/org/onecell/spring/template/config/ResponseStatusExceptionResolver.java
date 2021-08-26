package org.onecell.spring.template.config;

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

/*
*
   발생되는 모든 Exception은 이곳에서 제일 먼저 잡힌다.
   만약 반환이 null이라면 해당 HandlerExceptionResolver 는 처리되지 않고 넘어간다.
   @EnableWebMvc 이 선언된 WebMvcConfigurer에서 추가 가능하다.
    configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) 여기에 추가하면
    Controller내의 ExceptionHandler등 에러 핸들링은 다 무시 되기 때문에
    꼭 extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers)  이 곳에서 추가하여야 한다.


  public class MvcConfig implements WebMvcConfigurer {


  @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0,new RestResponseStatusExceptionResolver());
       //resolvers.add(new RestResponseStatusExceptionResolver());
    }

*만약 반환이 null이라면 해당 HandlerExceptionResolver 는 처리되지 않고 넘어가서 다른 HandlerExceptionResolver 가 처리하게 된다.
처리를 해당 HandlerExceptionResolver 에서 했다면 new ModelAndView()를 반환하여 처리를 마무리 해야한다.
response 에 반환을 직접넣든 ModelAndView를 이용하던 마음대로다.

Rest 요청시에 대한 404 Not Found 에러에 대한 Rest 응답도 이곳에서 처리하는 것이 좋다.
* */
//@Component
public class ResponseStatusExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        response.setStatus(400);
        ModelAndView modelAndView = new ModelAndView();

        return  null;
    }



}
