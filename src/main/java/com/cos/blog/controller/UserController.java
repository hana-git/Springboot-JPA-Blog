package com.cos.blog.controller;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

//인증이 안된 사용자들이 출입할 수 있는 경로
//1. /auth 이하
//2. 그냥 주소가 / 이면 index.jsp 허용
//3. static 이하 허용

@Controller
public class UserController {

    @Value("${cos.key}")
    private String cosKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code){  //@ResponseBody data를 리턴해주는 컨트롤러 함수

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

        //응답데이터 담기
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("카카오 엑세스 토큰 : " + oAuthToken.getAccess_token());



        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfilerequest =
                new HttpEntity<>(headers2);

        //HTTP 요청 - POST / response 변수 응답
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfilerequest,  //body 데이터
                String.class //응답 데이터
        );

        //응답데이터 담기
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //User오브젝트 : username, password, email 필요
        System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
        System.out.println("카카오 이메일 : "+ kakaoProfile.getKakao_account().getEmail());

        System.out.println("blog서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("blog서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("blog서버 pw : " + cosKey);

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        //존재하는 회원인지 체크해서 가입처리
        User originUser = userService.회원찾기(kakaoUser.getUsername());

        if(originUser.getUsername() == null){ //존재하지 않는다면 가입처리
            System.out.println("기존 회원이 아니므로 자동 회원가입을 진행합니다.");
            userService.회원가입(kakaoUser);
        }

        //로그인처리
        System.out.println("자동 로그인을 진행합니다.");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return  "redirect:/";//response2.getBody();
    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }
}
