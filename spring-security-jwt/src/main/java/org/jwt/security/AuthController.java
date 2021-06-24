package org.jwt.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static org.istech.config.GlobalConfig.BASE_URL;

@RestController
@RequestMapping(value = {BASE_URL+"/auth"})
public class AuthController {

    @Setter
    @Getter
    public static class LoginDto{
        String id;
        String password;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtTokenDto{
        String jwt_token;
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

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getId(), loginDto.getPassword()));
        User userDetails = (User)authenticate.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtTokenDto(token);
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
