package com.cos.blog.test;

import lombok.*;

//@Getter
//@Setter
@Data  //getter + setter
@AllArgsConstructor //전체 생성자
@NoArgsConstructor  //빈 생성자
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

}
