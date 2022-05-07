package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //파일을 리턴함
public class TempControllerTest {
    @GetMapping("/temp/home")
    public String tempHome(){
        System.out.println("tempHome()");
        //파일리턴되는 기본경로 : src/main/resources/static
        //리턴명 : /home.html
        //따라서 풀경로 : src/main/resources/static/home.html
        return "/home.html"; // 슬래시를 꼭 붙여줘야 찾아감
    }

    @GetMapping("/temp/jsp")
    public String tempJsp(){
        //prefix: /WEB-INF/views/
        //suffix: .jsp
        //풀경로 : /WEB-INF/views/test.jsp
        return "test";
    }
}
