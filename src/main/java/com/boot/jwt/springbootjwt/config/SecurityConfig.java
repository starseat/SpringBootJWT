package com.boot.jwt.springbootjwt.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity _http) throws Exception {
        _http
                // HttpServeltRequest를 사용하는 요청들에 대한 접근 제한 설정
                .authorizeRequests()
                // [/api/hello] 요청은 인증없이 허용
                .antMatchers("/api/hello").permitAll()
                // 나머지 요청은 인증 받도록 설정
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity _web) {
        _web
                .ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico"

                );
    }
}
