package com.cos.blog.handler;

import com.cos.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice //어디에서나 접근 가능
@RestController
public class GlobalExceptionHandler {

    //@ExceptionHandler(value = IllegalArgumentException.class) //IllegalArgumentException만 탄다
    @ExceptionHandler(value = Exception.class) //모든 Exception이 탄다
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

}
