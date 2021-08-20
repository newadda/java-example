package org.onecell;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.Locale;

@RestController
@Validated
@RequestMapping(value = {"/test"})
public class TestController {
    @Validated
    @PreAuthorize("isAuthenticated() and hasAnyRole('ADMIN')")
    @RequestMapping(value = {"/test01"}, method = RequestMethod.GET)
    @ResponseBody
    public Object test01(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {

        DatasDto<Test01Dto> datas = new DatasDto<>();

        /*
        {
           "datas":null
        }
         */
        return datas;
    }

    @Validated
    @PreAuthorize("isAuthenticated() and hasAnyRole('ADMIN')")
    @RequestMapping(value = {"/test02"}, method = RequestMethod.GET)
    @ResponseBody
    public Object test02(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {

        DatasDto<Test01Dto> datas = new DatasDto<>();
        Test01Dto dto = new Test01Dto();
        datas.setDatas(new LinkedList<>());

          /*
        {
           "datas":[]
        }
         */
        return datas;
    }



    @Validated
    @PreAuthorize("isAuthenticated() and hasAnyRole('ADMIN')")
    @RequestMapping(value = {"/test03"}, method = RequestMethod.GET)
    @ResponseBody
    public Object test03(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {

        DatasDto<Test01Dto> datas = new DatasDto<>();

        datas.setDatas(new LinkedList<>());

        Test01Dto dto = new Test01Dto();
        dto.setB("1");
        datas.getDatas().add(dto);

          /*
        {
           "datas":[{
              "a":null,
              "b":"1",
              "sub":null
           }]
        }
         */
        return datas;
    }


    @Validated
    @PreAuthorize("isAuthenticated() and hasAnyRole('ADMIN')")
    @RequestMapping(value = {"/test04"}, method = RequestMethod.GET)
    @ResponseBody
    public Object test04(HttpServletRequest request, HttpServletResponse response, Locale locale
    ) {

        DatasDto<Test01Dto> datas = new DatasDto<>();

        datas.setDatas(new LinkedList<>());

        Test01Dto dto = new Test01Dto();
        Test02Dto subDto = new Test02Dto();
        dto.setB("1");
        subDto.setC("4");
        dto.setSub(subDto);
        datas.getDatas().add(dto);

          /*
        {
           "datas":[{
              "a":null,
              "b":"1",
              "sub":{c:"4"}
           }]
        }
         */
        return datas;
    }

}
