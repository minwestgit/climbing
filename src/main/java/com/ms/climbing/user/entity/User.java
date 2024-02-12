package com.ms.climbing.user.entity;

import com.ms.climbing.common.Base;
import com.ms.climbing.user.dto.SignUpRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_info")
@Entity
public class User extends Base {

    @Id
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nickName;
    @Column
    private String instagram;

    @Builder
    private User(String userId, String password, String name, String nickName, String instagram) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.instagram = instagram;
    }

    public static User createSignUpUser(SignUpRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .nickName(request.getNickName())
                .instagram(request.getInstagram())
                .build();
    }
}