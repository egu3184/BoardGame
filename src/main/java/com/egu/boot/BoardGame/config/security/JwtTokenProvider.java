package com.egu.boot.BoardGame.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.egu.boot.BoardGame.model.RoleType;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@CrossOrigin(origins = "*")
public class JwtTokenProvider {

	@Value("${spring.jwt.secret}")
	private String secretKey; 
	
	private long tokenValidMilisecond = 1000L * 60 * 60; // 토큰 유효 시간 : 1시간 
	private final UserService userService;
	
	public String test() {
		return secretKey;
	}
	
	@PostConstruct 
	//생명주기 메서드 지정. 이 객체의 생성 초기화가 끝나고 이 객체를 의존하는 곳에 주입하기 직전에 호출된다.
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	//JWT 토큰 생성
	public String createToken(String userId, List<String> roles) {
		//Claims 클래스는 Jwt 속성 정보를 가지는 클래스.
		Claims claims = Jwts.claims().setSubject(userId);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims)	//데이터
				.setIssuedAt(now) //토큰 발생 일자
				.setExpiration(new Date(now.getTime()+tokenValidMilisecond)) //만료일자. 현재시간+유효시간
				.signWith(SignatureAlgorithm.HS256, secretKey)//시그니처. 알고리즘+시크릿키
				.compact();
	}
	
	//JWT 토큰으로 인증 정보를 조회
	public Authentication getAuthentication(String token) {
		// getUserId 메서드로 토큰으로부터 userId를 받고 loadUserByUsername으로 회원 정보 조회
		User userDetails = (User) userService.loadUserByUsername(this.getUserId(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	//JWT토큰에서 회원 구별 정보(userId) 추출
	public String getUserId(String token) {
		System.out.println("getUserId에서 가져온 토큰"+token);
		//받은 토큰을 시크릿키로 파싱하여 subject에 넣어둔 id값 꺼내오기
		String token1 = Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(token).getBody().getSubject();
		System.out.println("getUserId에서 가져온 id값"+token1);
		return token1;
	}
	
	//Request의 Header에서 token을 파싱 : "X-AUTH-TOKEN: jwt토큰"
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	//JWT 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			boolean validation = !claims.getBody().getExpiration().before(new Date());
			return validation;
		} catch (Exception e) {
			return false;
		}
	}



}
