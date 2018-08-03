package org.onecellboy.web.api.controller;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.onecellboy.web.api.response.error.ApiError;

/**
 * Exception을 핸들링 하기 위해 만들었지만 controller 내에서 자체 처리하는게 나아 보여서 그렇게 했다.
 *
 */
//@RestControllerAdvice
public class RestExceptionHandler {

    //protected exceptionHandler()

    /**
     * 잘못된 Path Param 에 대한 Exception 핸들링
     *
     * @param e
     * @param request
     * @return
     */

    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ApiError MissingServletRequestParameterExceptionHandling(Exception e, WebRequest request)
    {
        ApiError error = new ApiError();
        error.setStatus(444);
        error.setMessage(e.getMessage());
        return error;
    }


    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ApiError ServletRequestBindingExceptionHandling(Exception e, WebRequest request)
    {
        ApiError error = new ApiError();
        error.setStatus(444);
        error.setMessage(e.getMessage());
        return error;
    }



}
