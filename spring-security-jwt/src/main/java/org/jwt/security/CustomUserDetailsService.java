package org.mulbangul.server.security;

import org.istech.db.entity.TB_INFO_USER;
import org.istech.libs.Codes;
import org.istech.repository.impl.TbInfoUserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomUserDetailsService implements UserDetailsService {
    UserRepo repo;
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
}
