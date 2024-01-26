package com.ms.climbing.security;

import com.ms.climbing.exception.CommonErrorCode;
import com.ms.climbing.exception.CommonException;
import com.ms.climbing.user.dto.UserLoginDto;
import com.ms.climbing.user.entity.User;
import com.ms.climbing.user.service.UserAuthService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component @Slf4j
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("security.jwt.access-expiration-time")
    private long accessTokenValidTime;
    @Value("security.jwt.refresh-expiration-time")
    private long refreshTokenValidTime;

    private final UserAuthService userAuthService;

    //객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    //토큰 생성
    public String createAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUserId()); //JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //정보 저장
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) //토큰 유효시각 설정
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //암호화 알고리즘, secret 값 설정
                .compact();
    }

    public String createRefreshToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUserId()); //JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //정보 저장
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) //토큰 유효시각 설정
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //암호화 알고리즘, secret 값 설정
                .compact();
    }

    //인증 정보 조회
    public Authentication getAuthentication(String token) {
        //Spring Security에서 제공하는 메서드 override해서 사용해야 함
        UserLoginDto userLoginDto = userAuthService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userLoginDto.getUser(), "", userLoginDto.getAuthorities());
    }

    //토큰에서 User 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    //토큰 유효성, 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(ExpiredJwtException e) {
            log.error(e.getMessage());
            throw new CommonException(CommonErrorCode.EXPIRED_TOKEN);
        } catch(JwtException e) {
            log.error(e.getMessage());
            throw new CommonException(CommonErrorCode.INVALD_TOKEN);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return false;
        }
    }

    //Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public Long getExpiration(String token) {
        Date date = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        Long now = new Date().getTime();
        return (date.getTime() - now);
    }

    public String extractToken(HttpServletRequest request) {
        String token = resolveToken(request);
        if(token != null && token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "");
        }
        return null;
    }

}
