package com.cos.blog.auth;

import com.cos.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails타입의 오브젝트를
//스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다.
public class PrincipalDetail implements UserDetails {
    private User user; // 품고있다(컴포지션)

    public PrincipalDetail(User user){
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정이 만료되지 않았는지 리턴한다. (true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있지 않았는지 리턴한다. (true: 잠기지않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지 리턴한다. (true : 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화(사용가능)인지 리턴한다. (true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    //어떤 권한을 가진건지 계정의 권한을 리턴한다. 권한이 여러개일경우 루프를 돌아야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();

        /*collectors.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + user.getRole(); // ROLE_ 붙는건 규칙임
            }
        });*/
        //람다식 표현
        collectors.add(() -> {
            return "ROLE_" + user.getRole(); // ROLE_ 붙는건 규칙임
        });

        return collectors;
    }

}
