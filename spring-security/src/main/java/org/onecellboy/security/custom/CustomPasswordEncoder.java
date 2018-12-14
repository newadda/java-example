package org.onecellboy.security.custom;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
    PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private String cryptCode = "bcrypt";
    /***
     *  인코딩 구현부
     * @param rawPassword
     * @return
     */

    // 인코딩하면 자동으로 {bcrypt} 가 인코딩된 값 앞에 붙는다.
    @Override
    public String encode(CharSequence rawPassword) {

        return delegatingPasswordEncoder.encode(rawPassword).replace("{bcrypt}","");
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
