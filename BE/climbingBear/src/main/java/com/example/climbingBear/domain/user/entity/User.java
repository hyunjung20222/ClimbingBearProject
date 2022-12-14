package com.example.climbingBear.domain.user.entity;

import lombok.Builder;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
//@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private long userSeq;

    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "pw", nullable = false, unique = true)
    private String pw;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;
    @Column(name = "refresh_token")
    private String refreshToken;
    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
    @Builder
    public User(String id, String pw, String nickname) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
    }


}