package org.jwt.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.*;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 인증 오류시   failureHandler.onAuthenticationFailure(req,res,e); 를 통해 이곳에서 응답을 바로 한다.
 *
 */
public class JwtTokenFilter2  extends OncePerRequestFilter {
    public static class JwtTokenconfig{
        private long access_token_validity_seconds = 20*365*24*60*60;
        private String signing_key = "aleialajsdkfl111111111111111111111111111111";
        private String token_prefix = "Bearer ";
        private String header_string = "Authorization";

        public long getAccess_token_validity_seconds() {
            return access_token_validity_seconds;
        }

        public String getSigning_key() {
            return signing_key;
        }

        public String getToken_prefix() {
            return token_prefix;
        }

        public String getHeader_string() {
            return header_string;
        }
    }



    private AuthenticationFailureHandler failureHandler  = new SimpleUrlAuthenticationFailureHandler();
    private RememberMeServices rememberMeServices = new NullRememberMeServices();


    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final  JwtTokenconfig config;

    public JwtTokenFilter2(JwtTokenUtil jwtTokenUtil,
                          UserDetailsService userDetailsService) {
        this(new JwtTokenconfig(),jwtTokenUtil,userDetailsService);
    }

    public JwtTokenFilter2(JwtTokenconfig config ,JwtTokenUtil jwtTokenUtil,
                          UserDetailsService userDetailsService) {
        this.config = config;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        try {
            process(req, res, chain);
            chain.doFilter(req, res);
        }catch (AuthenticationException e)
        {
            failureHandler.onAuthenticationFailure(req,res,e);
        }


        /// 인증이 되었든 안되었든 다음 필터로 넘어가면 된다. 보통 인증 필터들은 앞부분에서
        //// SecurityContextHolder.getContext().getAuthentication().isAuthenticated() 를 확인하여 인증이 되었으면 바로 다음으로 필터를 넘긴다.

    }


    private void process(HttpServletRequest req,
                         HttpServletResponse res,
                         FilterChain chain) throws ServletException, IOException
    {
        String header = req.getHeader(config.getHeader_string());

        if(header == null)
        {
            return ;
        }

        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(config.getToken_prefix())) {

            authToken = header.replace(config.getToken_prefix(),"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {

                logger.error("an error occured during getting username from token", e);
                throw new AuthenticationServiceException("an error occured during getting username from token",e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
                throw new AuthenticationServiceException("the token is expired and not valid anymore",e);
                /* 예시이다.
                // unsuccessfulAuthentication(req,res,new AuthenticationServiceException("기간 만료..."));
                // return
                */
            }catch (Exception e)
            {
                logger.warn("Exception", e);
                throw new AuthenticationServiceException("the token is expired and not valid anymore",e);
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //authentication.setAuthenticated(true); // 생성시 true이므로 안해줘도 되지만 기록을 위해 남긴다.
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                logger.info("authenticated user " + username + ", setting security context");
                /// 인증된 토큰을  SecurityContextHolder 에 넣어 준다. 인증되었다고 판단.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                throw new AuthenticationServiceException("not found user");
            }
        }


    }


    /// doFilterInternal() 에서 Exception 발생시 이를 AuthenticationException 을 상속한 Exception으로 만들어서 처리해라.
    /// 예를 들어 기간 만료등이 이에 해당할 수 있다.
    /// 원하는 형태의 body를 만들어라.
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.logger.trace("Failed to process authentication request", failed);
        this.logger.trace("Cleared SecurityContextHolder");
        this.logger.trace("Handling authentication failure");
        this.rememberMeServices.loginFail(request, response);
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    /// 혹시나 해서 놔둠
    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "successHandler cannot be null");
        this.successHandler = successHandler;
    }
}
