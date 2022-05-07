package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    //service를 DI한다.
    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("UserApiController 호출됨");
        //실제로 DB에 insert하고 아래에서 return되면 됨.
        user.setRole(RoleType.USER);
        int result = userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
    }
}
