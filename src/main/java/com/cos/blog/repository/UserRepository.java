package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//DAO
//자동으로 bean 등록이 된다.
//@Repository 생략가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {

    //select * from user Where username = 1?;
    Optional<User> findByUsername(String username);

}

//JPA Naming 쿼리
//select * from user Where username = ? AND password = ? 가 동작한다. (? 는 파라미터)
//User findByUsernameAndPassword(String username, String password);

