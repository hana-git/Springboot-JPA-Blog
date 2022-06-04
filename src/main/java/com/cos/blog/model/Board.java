package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content; //섬머노트 라이브러리 사용할거임(html섞여서 디자인됨, 용량이 커짐)

    //@ColumnDefault("0") //기본값 0
    private int count; //조회수

    @ManyToOne(fetch = FetchType.EAGER) //연관관계 Many = board, User=one 1명의 유저는 여러개 게시글 작성가능
    @JoinColumn(name = "userId")
    private User user; //DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    //fetch : 기본전략 LAZY - 필요할때 땡겨온다 (댓글을 바로보여주지않고 펼치기 등 기능이 있을때)
    //fetch : 기본전략 EAGER - 무조건 들고온다
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //1개의 게시글은 여러개 답변을 가진다.
    // mappedBy: 연관관계의 주인이 아니다. (난 FK가 아니에요) DB에 컬럼을 만들지마세요.
    //"board" 는 Reply 테이블의 필드이름
    @JsonIgnoreProperties({"board"})  //무한참조가 일어날수있어 Reply 객체의 board는 무시한다
    @OrderBy("id desc")
    private List<Reply> replies;

    @CreationTimestamp
    private Timestamp createDate;
}
