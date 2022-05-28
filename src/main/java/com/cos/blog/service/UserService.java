package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service  //스프링이 컴포넌트 스캔을 통해서 Bean 에 등록해줌(IoC해준다)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional //여러개의 트랜젝션이 성공하면 commit, 한개라도 실패하면 rollback
    public int 회원가입(User user){
        try{
            String rawPassword = user.getPassword(); //1234 원문pw
            String encPassword = encoder.encode(rawPassword); //해쉬pw
            user.setPassword(encPassword);
            //실제로 DB에 insert하고 아래에서 return되면 됨.
            user.setRole(RoleType.USER);
            userRepository.save(user);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("User Service : 회원가입() : " + e.getMessage());
        }
        return -1;
    }

    @Transactional
    public void 회원수정(User user){
        //수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정한다.
        //select 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화하기 위함임
        //영속화된 오브젝트를 변경하면 자동으로 DB에 update를 날려주기때문
        User persistance = userRepository.findById(user.getId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패");
                });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());

        //회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 됨
        //영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.

        }

    /*
    @Transactional(readOnly = true) //select 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성유지)
    public User 로그인(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
