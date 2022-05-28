package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

//인증이 안된 사용자들이 출입할 수 있는 경로
//1. /auth 이하
//2. 그냥 주소가 / 이면 index.jsp 허용
//3. static 이하 허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){  //@ResponseBody data를 리턴해주는 컨트롤러 함수

        //POST방식으로 key=value 데이터를 요청한다(카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","50b5f26f736f45195b0d4a79b65920a2");
        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
        params.add("code",code); //동적 값

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params,headers);

        //HTTP 요청 - POST / response 변수 응답
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,  //body 데이터
                String.class //응답 데이터
        );

        return "카카오 토큰 요청 / 토큰요청에 대한 응답 : " + response;
    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }
}
