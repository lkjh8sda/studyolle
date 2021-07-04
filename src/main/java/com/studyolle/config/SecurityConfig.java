package com.studyolle.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//내가 시큐리티 설정을 다 하겠다
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    //내가 원하는 특정 요청들은 시큐리티 체크를 피해가도록 권한 부여할꺼
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/login","/sign-up","/check-email","/check-email-token",
                        "/email-login","/check-email-login","/login-link").permitAll()
                //프로필 요청은 get만 허용
                .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()
                //나머지 요청들은 모두 인증해야 한다.
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        static 에있는 소스들 인증 하지 마라
        web.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
