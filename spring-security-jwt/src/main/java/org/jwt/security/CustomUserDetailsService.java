package org.jwt.security;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    /*UserRepo repo;
    public CustomUserDetailsService(UserRepo repo) {
        this.repo = repo;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity byUserId = repo.findByUserId(username);

        User.UserBuilder userBuilder = User.withUsername(username).password(byUserId.getUser_pwd());
        /// 관리자
        if(byUserId.getUser_lv().equals("2"))
        {
            userBuilder.roles(Codes.Authorities.ADMIN);
        }else
        {
            userBuilder.roles(Codes.Authorities.VISITOR);
        }

        return userBuilder.build();

    }



    */
}
