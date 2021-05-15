package com.studyolle.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity         //연관관계가 복잡해질때 순환참조를 통한 stack overflow발생가능하기 때문에 id만 사용
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String nickname;

    private String password;
    //이메일 검증 여부
    private boolean emailVerified;
    //이메일 검증 때 사용할 토큰
    private String emailCheckToken;
    //가입날짜
    private LocalDateTime joinedAt;
    //프로필 정보
    private String bio;
    //개인 사이트 url
    private String url;
    //직업
    private String occupation;
    //거주지
    private String location; // varchar(255)

    //이미지 // 255자보다 커질수가 있음, lob으로 매핑, 이미지는 유저 로딩할 때 종종 쓰일꺼라 그때그때 가져오게 eager로
    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdateByEmail;

    private boolean studyUpdateByWeb;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }
}
