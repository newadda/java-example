package org.onecellboy.web.api.controller;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.onecellboy.common.spring.validation.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.onecellboy.web.api.response.error.ApiError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

@RestController
@Validated
public class Example01Controller extends  AbstractController{

    @Autowired
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder){
        LocalValidatorFactoryBean a = new LocalValidatorFactoryBean();
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:/org/onecellboy/common/spring/ValidationMessages");
        a.setValidationMessageSource(reloadableResourceBundleMessageSource);

        String validationMessages = new AggregateResourceBundleLocator(
                Arrays.asList(
                        "org/onecellboy/common/spring/ValidationMessages",
                        "ValidationMessages"
                )
        ).getResourceBundle(new Locale("")).getString("org.onecellboy.common.spring.validation.phone.message");

        String message = reloadableResourceBundleMessageSource.getMessage("org.onecellboy.common.spring.validation.phone.message", null, new Locale(""));
        binder.addValidators(a);

        Validator validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(
                        new ResourceBundleMessageInterpolator(
                                new AggregateResourceBundleLocator(
                                        Arrays.asList(
                                                "org/onecellboy/common/spring/ValidationMessages",
                                                "ValidationMessages"
                                        )
                                )
                        )
                )
                .buildValidatorFactory()
                .getValidator();


    }



    @RequestMapping(value = {"test"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String test(
@Phone
            @NotNull
                           @RequestParam(value = "id",required = true,name = "id")  String id,
                         @RequestParam Map<String,String> queryMap,
                         HttpServletRequest request, HttpServletResponse response)
    {

        String s = validator.getClass().toString();

        return "test";
    }

    @RequestMapping(value = {"test1"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String test1(@RequestParam(value = "id",required = true,name = "id")  String id,
                       @RequestParam Map<String,String> queryMap,
                       HttpServletRequest request, HttpServletResponse response)
    {

        return "test";
    }





}
