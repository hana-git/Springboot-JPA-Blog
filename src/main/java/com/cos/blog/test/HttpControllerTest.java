package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//인터넷 브라우저 요청은 무조건 get 요청 밖에 할 수 없다
//사용자가 요청하면 html로 응답 : @Controller
//사용자가 요청하면 data로 응답 : @RestController
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest : ";

    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member m = new Member(1, "ssar", "1234", "email");
        System.out.println(TAG+"getter : " + m.getId());
        m.setId(5000);
        System.out.println(TAG+"setter : " + m.getId());
        return "lombok test 완료";
    }

    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m){
        return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }
    //http://localhost:8080/http/post (insert)
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m){ //스프링부트의 MessageConverter 클래스가 매핑해줌
        return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }
    //http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m){
        return "put 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }
    //http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest(@RequestBody Member m){
        return "delete 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }
}
