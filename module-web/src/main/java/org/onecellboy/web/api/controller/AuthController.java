package org.onecellboy.web.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.onecellboy.web.api.response.error.ApiError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
public class AuthController extends  AbstractController{

    public static final String TEST = "abc";


    @RequestMapping(value = {TEST+"d"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public ApiError test(@RequestParam(value = "id",required = true,name = "id")  int id,
                         @RequestParam Map<String,String> queryMap,
                         HttpServletRequest request, HttpServletResponse response)
    {

        @NotNull(message = "Name cannot be null")
        String a=null;
/*
        String meassage = "";
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<String>> validate = validator.validate(a);
        for (ConstraintViolation<String> violation : validate) {

            meassage+=" "+violation.getMessage();
        }*/

if(1==1)
        throw new RuntimeException();
        ApiError error = new ApiError();
        error.setStatus(440);
        error.setMessage("");
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return error;
    }







}
