package org.onecellboy.web.security.oauth2.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

    PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private String cryptCode = "bcrypt";
    /***
     *  인코딩 구현부
     *
     *  인코딩 되면 암호화된 값 앞에 {bcrypt} 문자열이 앞에 붙는다.
     *  예) {bcrypt}$2a$04$H.zd7Jo0nACJS4OY4Yas1OYRpv/j3y1q2igWGARnBqlBMvl9QonJu
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {

        return delegatingPasswordEncoder.encode(rawPassword);
    }

    /**
     * 패스워드 비교
     * DelegatingPasswordEncoder 의 경우 인코딩된 암호화 값 앞에 {bcrypt} 와 같이 인코딩 방식을 적어주어야 한다.
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return delegatingPasswordEncoder.matches(rawPassword,"{"+cryptCode+"}"+encodedPassword);
    }
}
