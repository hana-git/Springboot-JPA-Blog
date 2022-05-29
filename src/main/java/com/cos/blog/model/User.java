package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
//JPA는 ORM이다 : Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity //클래스를 테이블화한다. user 클래스가 MySQL에 테이블이 자동생성된다.
//@DynamicInsert //null 인 데이터는 제외하고 insert
public class User {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //IDENTITY : 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 오라클 : 시퀀스, mysql : auto_increment
    private int id; //시퀀스, auto_increment

    @Column(nullable = false, length = 100, unique = true)
    private String username;//id

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("'user'")
    //private String role; //나중에 Enum을 쓰는게 좋다. //도메인 설정 (범위) admin, user, manager 권한
    @Enumerated(EnumType.STRING) //DB는 RoleType이라는게 없다. string 이라고 알려줌
    private RoleType role; //ADMIN, USER 두개로 강제지정

    private String oauth; //oauth 사용자인지 구분, 일반가입시 null

    @CreationTimestamp //시간이 자동으로 입력된다
    private Timestamp createDate;



}
