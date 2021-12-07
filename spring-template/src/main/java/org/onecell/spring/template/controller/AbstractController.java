package org.onecell.spring.template.controller;


import org.onecell.spring.template.dto.error.ErrorRootDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
//import org.springframework.security.access.AccessDeniedException;
import java.util.Locale;

/**
 * Restful Controller 에 대한 Exception Handling 을 위해 만들었다.
 *
 */
@Configuration
public class AbstractController {

    private static final Logger log= LoggerFactory.getLogger(AbstractController.class);



    @Autowired
    @Qualifier(value = "messageSource")
    AbstractResourceBasedMessageSource messageSource;

    /**
     *   메소드 파라미터에서
     *  "@PathVariable ,@RequestParam 등 선언하고 required = true 일 때 해당 key 자체가 존재하지 않을때 발생한다.
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Object MissingServletRequestParameterExceptionHandling(Exception e, WebRequest request, Locale locale)
    {

        ErrorRootDto dto = new ErrorRootDto();
        dto.getError().setCode(e.getClass().getSimpleName());
        dto.getError().setUser_message( e.getLocalizedMessage());
        return dto;
    }


    /**
     *  메소드 파라미터에서 Validation 을 하면 발생한다.
     * "@PathVariable ,@RequestParam 시, Type(Casting 이 되지 않으면)이 맞지 않으면 발생한다."
     *  exception의 getName()을 통해 파라미터 이름을 확인할 수 있다.
     * @param
     * @param request
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public Object ServletRequestBindingExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        ErrorRootDto dto = new ErrorRootDto();
        dto.getError().setCode(e.getClass().getSimpleName());
        dto.getError().setUser_message( e.getLocalizedMessage());
        return dto;
    }

    /**
     * " @PathVariable @RequestParam 에 Validation 오류시 발생한다."
     *  또는 @Validation에 대한 BindingResult가 없을 시에도 이곳으로 Exception 이 넘어온다.
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Object ConstraintViolationExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        ErrorRootDto dto = new ErrorRootDto();
        dto.getError().setCode(e.getClass().getName());
        dto.getError().setUser_message(e.getLocalizedMessage());
        return dto;

    }


    /**
     * Binding 시 BindingResult가 없다면 이곳으로 반환된다.
     * 객체에 대한 Binding 시  @Validation 에 대한 제약조건 오류시 발생한다.
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public Object BindExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        BindException castExcep = (BindException)  e;
        FieldError fieldError = castExcep.getFieldError();

        ErrorRootDto dto = new ErrorRootDto();
        dto.getError().setCode(castExcep.getClass().getName());
        dto.getError().setUser_message(fieldError.getDefaultMessage());
        return dto;
    }


    /**
     * HttpMessageNotReadableException : json이나 xml의 파싱불가시 발생한다. @RequestBody 시 binding 할 클래스의 타입과 실제 json 타입이 다르면 파싱하지 못한다.
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) //500 에러
    @ResponseBody
    @ExceptionHandler({Exception.class,RuntimeException.class})
    public Object ExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        log.warn("RuntimeException",e);
        ErrorRootDto dto = new ErrorRootDto();
        dto.getError().setCode(e.getClass().getSimpleName());
        dto.getError().setUser_message(e.getLocalizedMessage());
        return dto;
    }


    /**
     *
     * 인증은 되었으나 권한이 없을 경우. ( 또한 Authorization 헤더가 없는 경우도 결국 권한이 없는 것이다.)
     *
     * @param e
     * @param request
     * @return
     */
   /* @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN) //403 에러
    @ExceptionHandler({ AccessDeniedException.class})
    public Object RuntimeExceptionHandling(RuntimeException e, WebRequest request)
    {

         ErrorRootDto dto = new ErrorRootDto();
        dto.getError().setCode(e.getClass().getSimpleName());
        dto.getError().setUser_message(e.getLocalizedMessage());
        return dto;
    }*/

    /**
     *
     * 인증 실패시 에러
     * authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
     * 코드로 직접 인증시 해당 인증 메소드에서 인증실패(AuthenticationException)을 발생시킨다.
     *
     * @param e
     * @param request
     * @return
     */
  /*  @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED) //403 에러
    @ExceptionHandler({ AuthenticationException.class})
    public Object AuthenticationExceptionHandling(AuthenticationException e, WebRequest request)
    {
      ErrorRootDto dto = new ErrorRootDto();
        dto.getError().setCode(e.getClass().getSimpleName());
        dto.getError().setUser_message(e.getLocalizedMessage());
        return dto;
    }*/
/*
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //500 에러
    @ResponseBody
    @ExceptionHandler({ServiceException.class})
    public Object ServiceExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        ErrorRootDto dto = new ErrorRootDto();
        dto.getError().setCode(e.getClass().getSimpleName());
        dto.getError().setUser_message(e.getLocalizedMessage());
        return dto;
    }
*/

}
