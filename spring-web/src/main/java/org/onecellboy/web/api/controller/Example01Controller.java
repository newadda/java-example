package org.onecellboy.web.api.controller;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.onecellboy.common.spring.validation.Phone;
import org.onecellboy.common.spring.validation.PhoneConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.onecellboy.web.api.response.error.ApiError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@RestController
@Validated
public class Example01Controller extends  AbstractController{

    @Autowired
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder){
/*
        LocalValidatorFactoryBean a = new LocalValidatorFactoryBean();
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:/org/onecellboy/common/spring/ValidationMessages");
        String message1 = reloadableResourceBundleMessageSource.getMessage("org.onecellboy.common.spring.validation.phone.message", null, new Locale("ko"));
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        a.setValidationMessageSource(reloadableResourceBundleMessageSource);
        a.afterPropertiesSet();



        binder.addValidators(a);

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

        LocalValidatorFactoryBean ab = new LocalValidatorFactoryBean();
        ab.setMessageInterpolator(
                new ResourceBundleMessageInterpolator(
                        new AggregateResourceBundleLocator(
                                Arrays.asList(
                                        "org/onecellboy/common/spring/ValidationMessages",
                                        "ValidationMessages"
                                )
                        )
                )
        );
*/
/*
        ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasenames("classpath:/ValidationMessages","classpath:/org/onecellboy/common/spring/ValidationMessages");

        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
            validatorFactoryBean.setValidationMessageSource(bundle);


        binder.setValidator(validatorFactoryBean);
*/


       // binder.setValidator(ab);
    }



    @RequestMapping(value = {"test"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String test(

@Phone
@Email
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



        Test t = new Test();
        t.p="asdf";



        Set<ConstraintViolation<Test>> validate = validator.validate(t);


        return "test";
    }

    @RequestMapping(value = {"test3/{id}"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String test2(@Validated @ModelAttribute  Test test1,BindingResult result2,@Validated @ModelAttribute  Test2 test3,BindingResult result,
                        HttpServletRequest request, HttpServletResponse response)
    {

        ObjectError next = result2.getAllErrors().iterator().next();
        String objectName = next.getObjectName();
        Object[] arguments = next.getArguments();
        String code = next.getCode();

        FieldError fieldError = result2.getFieldError();
        String field = fieldError.getField();
        String code1 = fieldError.getCode();
        Object rejectedValue = fieldError.getRejectedValue();
        Object[] arguments1 = fieldError.getArguments();


        Test t = new Test();
        t.p="asdf";
        result2.rejectValue("p","org.onecellboy.common.spring.validation.phone.message","{org.onecellboy.common.spring.validation.phone.message}");
        result2.addError(new FieldError("p","org.onecellboy.common.spring.validation.phone.message","{org.onecellboy.common.spring.validation.phone.message}"));

        Set<ConstraintViolation<Test>> validate = validator.validate(t);


        return "test";
    }
    @RequestMapping(value = {"authrequried"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String authRequried()
    {
        return "test";
    }


    public  class Test{
        @Phone
        @NotNull
        String p;


        int t;

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }
    }

    public  class Test2{
        @Phone
        String p;

        int id;

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }





}
