package com.boot.jwt.springbootjwt.config;


import com.boot.jwt.springbootjwt.jwt.JwtAccessDeniedHandler;
import com.boot.jwt.springbootjwt.jwt.JwtAuthenticationEntryPoint;
import com.boot.jwt.springbootjwt.jwt.JwtSecurityConfig;
import com.boot.jwt.springbootjwt.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // @PreAuthorize 어노테이션을 메소드 단위로 추가하기 위함
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .cors().disable()
                .csrf().disable() // // 토큰 방식을 사용하기 때문에 csrf disable

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2-console 을 위한 설정
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()

                // 세션을 사용하지 않기 때문에 세션 설정 STATELESS
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                .and()
//                .formLogin().disable()
                // HttpServeltRequest를 사용하는 요청들에 대한 접근 제한 설정
                .authorizeRequests()
                // 아래 요청들은 토큰이 없는 상태에서 요청이 들어오기때문에 permitAll 설정
                // [/api/hello] 요청은 인증없이 허용
                .antMatchers("/api/hello").permitAll()
                // 로그인 API
                .antMatchers("/api/authenticate").permitAll()
                // 회원가입 API
                .antMatchers("/api/signup").permitAll()
                // 나머지 요청은 인증 받도록 설정
                .anyRequest().authenticated()

                // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico"

                );
    }



}
