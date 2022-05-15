package com.cos.blog.config;

import org.aspectj.weaver.bcel.BcelAccessForInlineMunger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//빈등록의미 : spring 컨테이너에서 객체를 관리할 수 있게 하는것
@Configuration //빈등록(IoC관리)
@EnableWebSecurity // security 필터가 등록이 된다 = 스프링 시큐리티가 활성화되어있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 의미
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean //IoC (BCryptPasswordEncoder 를 스프링이 관리한다)
    public BCryptPasswordEncoder encodePWD(){
        //String encPassword = new BCryptPasswordEncoder().encode("1234");//이 객체를 통해서 암호화할수있다
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf토큰 비활성화
                //요청시에 csrf 토큰이 없을경우 spring security 막아버리는게 기본 / 따라서 테스트할때는 disable하는게 좋음
                .authorizeRequests() //요청이 들어올때
                    .antMatchers("/","/auth/**", "/js/**", "/css/**", "/image/**") //auth 하위로 들어오는건
                    .permitAll()  // 모두 허용한다.
                    .anyRequest() //이게아닌 다른 모든 요청은
                    .authenticated()  //인증이 필요하다
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm"); //인증이 필요한 경우 이 페이지로 이동한다

    }
}
