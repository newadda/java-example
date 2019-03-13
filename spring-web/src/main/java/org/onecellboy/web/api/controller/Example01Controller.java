package org.onecellboy.web.api.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.constraints.ScriptAssert;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.onecellboy.web.api.response.error.ApiError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import javax.validation.Validator;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.function.Predicate;
import java.util.zip.DataFormatException;

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

    @RequestMapping(value = {"test2"},method = RequestMethod.POST,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String test2( @RequestBody  Test test1,  BindingResult bindingResult2 ,
                        HttpServletRequest request, HttpServletResponse response)
    {


        Test test4= new Test();
        final WebDataBinder binder = new WebDataBinder(test4);
        ServletRequestParameterPropertyValues values = new ServletRequestParameterPropertyValues(request);
        binder.bind(values);
        BindingResult bindingResult = binder.getBindingResult();



        User activeUser
                = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

/*
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
*/

        return "test";
    }


    @RequestMapping(value = {"test4/{id}"},method = RequestMethod.POST,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public Object test4(@RequestParam(value = "test",required = false) String test,
                        HttpServletRequest request, HttpServletResponse response)
    {

        return "test";
    }


    @RequestMapping(value = {"test4/me"},method = RequestMethod.POST,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public Object test4_1(@RequestParam(value = "test",required = false) String test,
                        HttpServletRequest request, HttpServletResponse response)
    {

        return "test";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"test123"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String test123(
                        HttpServletRequest request, HttpServletResponse response)
    {





        return "test";
    }


    @RequestMapping(value = {"test5"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String test5(@Validated @Email @RequestParam int id,
            HttpServletRequest request, HttpServletResponse response)
    {

        return "test";
    }

    @RequestMapping(value = {"authrequried"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String authRequried()
    {
        User activeUser
                = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "test";
    }

    @RequestMapping(value = {"error11"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String err() throws Exception
    {
        throw  new DataFormatException();
        //return "err";
    }

/*
    @Valid
    @ScriptAssert(
            message = "{org.onecellboy.common.spring.validation.phone.message}",
            lang = "javascript",
            script = "_this.p >= 'test'"
    )*/
    public static class Test{

        @NotNull
        String p;

        @NotNull
        Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

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

    @AssertTrue(message = "나이는 0보다 커야 하며 150보다 작아야 합니다.")
    public boolean isValidAge(){
        return id > 0 && id < 150;

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
