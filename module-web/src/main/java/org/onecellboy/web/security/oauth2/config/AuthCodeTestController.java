package org.onecellboy.web.security.oauth2.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthCodeTestController {
    @RequestMapping(value = "/authcodetest", method = RequestMethod.GET)
    public ModelAndView getEmployeeInfo() {

      return new ModelAndView("authcodetest");
    }
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = {"/authtest1"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String authtest1()
    {
        return "ok";
    }

    @RequestMapping(value = {"/authtest2"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String authtest2()
    {
        return "ok";
    }

    @RequestMapping(value = {"/authtest3"},method = RequestMethod.GET,produces = {"application/json"}/*,headers={"name=pankaj", "id=1"}*/)
    @ResponseBody
    public String authtest3()
    {
        return "ok";
    }

}
