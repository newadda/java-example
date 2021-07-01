package org.onecellboy.web.api.controller;

import com.google.protobuf.ServiceException;
import org.onecellboy.common.spring.validation.PhoneConstraintValidator;
import org.onecellboy.web.api.response.error.ApiErrorDto;
import org.onecellboy.web.api.response.error.ApiErrorReturnDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.onecellboy.web.api.response.error.ApiError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Locale;

/**
 * Restful Controller 에 대한 Exception Handling 을 위해 만들었다.
 *
 */
@Configuration
public class AbstractController {



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
    public ApiErrorReturnDto MissingServletRequestParameterExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        /*
        ApiErrorReturnDto returnDto = new ApiErrorReturnDto();

        ApiErrorDto apiErrorDto = new ApiErrorDto();
        apiErrorDto.setReason(ReasonCode.EXCEPTION.getTextCode());
        apiErrorDto.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorDto.setUser_message(messageSource.getMessage(MessageProperty.UNHANDLE_EXCEPTION_IN_INTERNAL,null,locale));

        ApiExceptionErrorDetailDto apiExceptionErrorDetailDto = new ApiExceptionErrorDetailDto();
        apiExceptionErrorDetailDto.setReason(ReasonCode.EXCEPTION.getTextCode());
        apiExceptionErrorDetailDto.setException(e.getClass().getSimpleName());
        apiExceptionErrorDetailDto.setMessage(e.getMessage());

        apiErrorDto.getErrors().add(apiExceptionErrorDetailDto);

        returnDto.setError(apiErrorDto);

        return returnDto;
        */
         return null;
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
    public ApiErrorReturnDto ServletRequestBindingExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        /*
        ApiErrorReturnDto returnDto = new ApiErrorReturnDto();

        ApiErrorDto apiErrorDto = new ApiErrorDto();
        apiErrorDto.setReason(ReasonCode.EXCEPTION.getTextCode());
        apiErrorDto.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorDto.setUser_message(messageSource.getMessage(MessageProperty.UNHANDLE_EXCEPTION_IN_INTERNAL,null,locale));

        ApiExceptionErrorDetailDto apiExceptionErrorDetailDto = new ApiExceptionErrorDetailDto();
        apiExceptionErrorDetailDto.setReason(ReasonCode.EXCEPTION.getTextCode());
        apiExceptionErrorDetailDto.setException(e.getClass().getSimpleName());
        apiExceptionErrorDetailDto.setMessage(e.getMessage());

        apiErrorDto.getErrors().add(apiExceptionErrorDetailDto);

        returnDto.setError(apiErrorDto);

        return returnDto;
        */
         return null;
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
    public ApiErrorReturnDto ConstraintViolationExceptionHandling(Exception e, WebRequest request, Locale locale)
    {

        ConstraintViolationException ex = (ConstraintViolationException)e;
/*
        ApiErrorReturnDto returnDto = new ApiErrorReturnDto();

        ApiErrorDto apiErrorDto = new ApiErrorDto();
        apiErrorDto.setReason(ReasonCode.INVALIDPARAMETER.getTextCode());
        apiErrorDto.setStatus(HttpStatus.BAD_REQUEST.value());

        apiErrorDto.setUser_message(ex.getConstraintViolations().iterator().next().getMessage());

        for(ConstraintViolation<?> item :ex.getConstraintViolations())
        {
            ApiInvalidParameterErrorDetailDto InvalidParameterErrorDetailDto = new ApiInvalidParameterErrorDetailDto();
            InvalidParameterErrorDetailDto.setReason(ReasonCode.INVALIDPARAMETER.getTextCode());
            InvalidParameterErrorDetailDto.setMessage(item.getMessage());
            InvalidParameterErrorDetailDto.setInvalid_location(item.getPropertyPath().toString());
            apiErrorDto.getErrors().add(InvalidParameterErrorDetailDto);
        }

        returnDto.setError(apiErrorDto);

        return returnDto;
*/
return null;
    }


    /**
     * Binding 시 BindingResult가 없다면 이곳으로 반환된다.
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public ApiErrorReturnDto BindExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        BindException ex = (BindException)e;
/*
        ApiErrorReturnDto returnDto = new ApiErrorReturnDto();

        ApiErrorDto apiErrorDto = new ApiErrorDto();
        apiErrorDto.setReason(ReasonCode.INVALIDPARAMETER.getTextCode());
        apiErrorDto.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorDto.setUser_message(messageSource.getMessage(MessageProperty.INVALIDPARAMETER,null,locale));

        apiErrorDto.setUser_message(ex.getAllErrors().iterator().next().getDefaultMessage());

        for(ObjectError item :ex.getAllErrors())
        {
            ApiInvalidParameterErrorDetailDto InvalidParameterErrorDetailDto = new ApiInvalidParameterErrorDetailDto();
            InvalidParameterErrorDetailDto.setReason(ReasonCode.INVALIDPARAMETER.getTextCode());
            InvalidParameterErrorDetailDto.setMessage(item.getDefaultMessage());
            InvalidParameterErrorDetailDto.setInvalid_location(item.getObjectName());
            apiErrorDto.getErrors().add(InvalidParameterErrorDetailDto);
        }

        returnDto.setError(apiErrorDto);

        return returnDto;
        */
        return null;
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
    public ApiErrorReturnDto ExceptionHandling(Exception e, WebRequest request, Locale locale)
    {
        return null;
    }


    /**
     *
     * 인증은 되었으나 권한이 없을 경우. ( 또한 Authorization 헤더가 없는 경우도 결국 권한이 없는 것이다.)
     *
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN) //403 에러
    @ExceptionHandler({ AccessDeniedException.class})
    public ApiErrorDto RuntimeExceptionHandling(RuntimeException e, WebRequest request)
    {

        return null;
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public ApiErrorReturnDto serviceExceptionHandling(ServiceException e, HttpServletRequest request, HttpServletResponse response)
    {
        return null;

    }



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
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN) //403 에러
    @ExceptionHandler({ AuthenticationException.class})
    public Object AuthenticationExceptionHandling(RuntimeException e, WebRequest request)
    {

        return null;
    }



}
