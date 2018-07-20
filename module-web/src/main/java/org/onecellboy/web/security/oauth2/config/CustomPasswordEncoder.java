package org.onecellboy.web.security.oauth2.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

    /***
     *  인코딩 구현부
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {

        return rawPassword.toString();
        
    }

    /**
     * 패스워드 비교
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
