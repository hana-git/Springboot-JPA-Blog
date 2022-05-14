package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service  //스프링이 컴포넌트 스캔을 통해서 Bean 에 등록해줌(IoC해준다)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional //여러개의 트랜젝션이 성공하면 commit, 한개라도 실패하면 rollback
    public int 회원가입(User user){
        try{
            userRepository.save(user);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("User Service : 회원가입() : " + e.getMessage());
        }
        return -1;
    }

    /*
    @Transactional(readOnly = true) //select 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성유지)
    public User 로그인(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
