package org.onecellboy.web.security.oauth2.config;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * User의 정보를 가져오는 실 코드가 들어가야 한다.
 *
 * 보통 DB에서 User 정보를 가져온다.
 *
 *
 */
@Service(value = "userDetailsService")
public class CustomUserDetailsService implements  UserDetailsService {
  //  @Autowired
    //AccountService accountService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("==========================================+"+username);
        
        return new CustomUserDetails();


    }

}
