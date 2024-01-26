package com.ms.climbing.user.service;

import com.ms.climbing.exception.CommonErrorCode;
import com.ms.climbing.exception.CommonException;
import com.ms.climbing.security.JwtTokenProvider;
import com.ms.climbing.security.TokenDto;
import com.ms.climbing.user.dto.LoginRequest;
import com.ms.climbing.user.dto.SignUpRequest;
import com.ms.climbing.user.entity.User;
import com.ms.climbing.user.exception.UserErrorCode;
import com.ms.climbing.user.exception.UserException;
import com.ms.climbing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service @Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void signup(SignUpRequest request) {
        if(userRepository.existsById(request.getUserId())) throw new UserException(UserErrorCode.DUPLICATE_ID); //ID 중복 체크
        userRepository.save(User.createSignUpUser(request));
    }

    public TokenDto login(LoginRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.ID_NOT_FOUND));
        //PW 체크
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.PASSWORD_INVALID);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        redisTemplate.opsForValue().set("RefreshToken:" + user.getUserId(), refreshToken, jwtTokenProvider.getExpiration(refreshToken), TimeUnit.MILLISECONDS);

        return TokenDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .authorizationType("Authorization")
                .grantType("Bearer")
                .build();
    }

    public void logout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //accessToken은 클라이언트에서 관리
        String refreshToken = redisTemplate.opsForValue().get("RefreshToken:" + user.getUserId());
        if (refreshToken != null){
            redisTemplate.delete("RefreshToken:" + user.getUserId());
        }
    }

    public String refreshToken(String token) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //Refresh Token 인증으로 가져온 데이터

        String refreshToken = redisTemplate.opsForValue().get("RefreshToken:" + user.getUserId());
        //refreshToken이 유효하지 않거나, 로그아웃되어 존재하지 않거나, 클라이언트가 보낸 토큰과 DB에 저장해둔 토큰이 같지 않다면 재발급 X
        if(!jwtTokenProvider.validateToken(refreshToken) || refreshToken == null || !refreshToken.equals(token))
            throw new CommonException(CommonErrorCode.INVALD_TOKEN);

        return jwtTokenProvider.createAccessToken(user);
    }


}
