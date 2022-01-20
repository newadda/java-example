package org.jwt.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


@RestController
@RequestMapping(value = {"/api/v1"+"/auth"})
public class AuthController {


    public static class LoginDto{
        String username;
        String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    public static class JwtTokenDto{
        String jwt_token;

        public JwtTokenDto(String jwt_token) {
            this.jwt_token = jwt_token;
        }

        public String getJwt_token() {
            return jwt_token;
        }

        public void setJwt_token(String jwt_token) {
            this.jwt_token = jwt_token;
        }
    }


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @Validated
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Object listLogin(HttpServletRequest request, HttpServletResponse response, Locale locale,
                                    @RequestBody LoginDto loginDto
    ) {
        Authentication authenticate= null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        }catch (AuthenticationException e)
        {
            throw new RuntimeException();
        }
        User userDetails = (User)authenticate.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtTokenDto(token);
        /*

        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setJwt_token(token);


        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();

        for(GrantedAuthority role:authorities)
        {
            String authority = role.getAuthority();
            jwtTokenDto.getRoles().add(authority);
        }

        return jwtTokenDto;



        * */

    }

    @Validated
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/test"}, method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Object listtest(HttpServletRequest request, HttpServletResponse response, Locale locale

    ) {



        return null;
    }



}
