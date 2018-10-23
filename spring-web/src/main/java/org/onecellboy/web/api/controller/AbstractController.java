package org.onecellboy.web.api.controller;

import org.onecellboy.common.spring.validation.PhoneConstraintValidator;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.onecellboy.web.api.response.error.ApiError;

import javax.validation.ConstraintViolationException;

/**
 * Restful Controller 에 대한 Exception Handling 을 위해 만들었다.
 *
 */
public class AbstractController {


    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ApiError MissingServletRequestParameterExceptionHandling(Exception e, WebRequest request)
    {
        ApiError error = new ApiError();
        error.setStatus(401);
        error.setMessage(e.getMessage());
        return error;
    }


    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ApiError ServletRequestBindingExceptionHandling(Exception e, WebRequest request)
    {
        ApiError error = new ApiError();
        error.setStatus(402);
        error.setMessage(e.getMessage());
        return error;
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ApiError ExceptionHandling(Exception e, WebRequest request)
    {
        ApiError error = new ApiError();
        error.setStatus(402);
        error.setMessage(e.getMessage());
        return error;
    }

    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    public ApiError RuntimeExceptionHandling(RuntimeException e, WebRequest request)
    {
        ApiError error = new ApiError();
        error.setStatus(402);
        error.setMessage(e.getMessage());
        return error;
    }


    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public ApiError RuntimeExceptionHandling(ConstraintViolationException e, WebRequest request)
    {

        ApiError error = new ApiError();
        error.setStatus(402);
        error.setMessage(e.getMessage());
        return error;
    }


}
