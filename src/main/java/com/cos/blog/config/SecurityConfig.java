package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//빈등록의미 : spring 컨테이너에서 객체를 관리할 수 있게 하는것
@Configuration //빈등록(IoC관리)
@EnableWebSecurity // security 필터가 등록이 된다 = 스프링 시큐리티가 활성화되어있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 의미
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //요청이 들어올때
                    .antMatchers("/auth/**") //auth 하위로 들어오는건
                    .permitAll()  // 모두 허용한다.
                    .anyRequest() //이게아닌 다른 모든 요청은
                    .authenticated()  //인증이 필요하다
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm"); //인증이 필요한 경우 이 페이지로 이동한다

    }
}
