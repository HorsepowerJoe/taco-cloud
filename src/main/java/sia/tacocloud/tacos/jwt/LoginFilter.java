package sia.tacocloud.tacos.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final Long ACCESS_TOKEN_EXPIRED_TIME;
    private final Long REFRESH_TOKEN_EXPIRED_TIME;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, Long ACCESS_TOKEN_EXPIRED_TIME, Long REFRESH_TOKEN_EXPIRED_TIME) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.ACCESS_TOKEN_EXPIRED_TIME = ACCESS_TOKEN_EXPIRED_TIME;
        this.REFRESH_TOKEN_EXPIRED_TIME = REFRESH_TOKEN_EXPIRED_TIME;
        
        setFilterProcessesUrl("/api/login");
    }

    
    



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        String role = authResult.getAuthorities().iterator().next().getAuthority();

        String access = jwtUtil.createJwt("access", username, role, ACCESS_TOKEN_EXPIRED_TIME);
        String refresh = jwtUtil.createJwt("refresh", username, role, REFRESH_TOKEN_EXPIRED_TIME);

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }

    private Cookie createCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(REFRESH_TOKEN_EXPIRED_TIME.intValue());
        //HTTPS의 경우
        //cookie.setSecure(true);
        //쿠키의 적용 범위 설정
        //cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

}
