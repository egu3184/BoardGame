package com.egu.boot.BoardGame.config.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.CustomJwtExceptionHandler;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.RoleType;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.service.CustomUserDetailsService;
import com.egu.boot.BoardGame.service.UserService;
import com.google.gson.JsonObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@CrossOrigin(origins = "*")
public class JwtTokenProvider {

	@Value("${spring.jwt.secret}")
	private String secretKey; 
	
	private long accessTokenValidMilisecond = 1000L * 60; // 토큰 유효 시간 : 5시간 1000L * 60 * 60 * 5
	private long refreshTokenValidMilisecond = 1000L * 60 * 3; // 토큰 유효 시간 : 60일 1000L * 60 * 60 * 24 * 60
	private final CustomUserDetailsService userDetailService;
	
	public String test() {
		return secretKey;
	}
	
	@PostConstruct 
	//생명주기 메서드 지정. 이 객체의 생성 초기화가 끝나고 이 객체를 의존하는 곳에 주입하기 직전에 호출된다.
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	//Jwt AccessToken 생성
	public String createAccessToken(String id, List<String> roles) {
		//Claims 클래스는 Jwt 속성 정보를 가지는 클래스.
		Claims claims = Jwts.claims().setSubject(id);
		claims.put("roles", roles);
		LocalDateTime now = LocalDateTime.now();
		return Jwts.builder()
				.setClaims(claims)	//데이터
				.setIssuedAt(Date.from(now.atZone(ZoneId.of("Asia/Seoul")).toInstant())) //토큰 발생 일자
				.setExpiration(Date.from(now.plusSeconds(3600).atZone(ZoneId.of("Asia/Seoul")).toInstant()))
//				.setExpiration(new Date(now.getTime()+accessTokenValidMilisecond)) //만료일자. 현재시간+유효시간
				.signWith(SignatureAlgorithm.HS256, secretKey)//시그니처. 알고리즘+시크릿키
				.compact();
	}
	
	//Jwt RefreshToken 생성
	public String createRefreshToken(String id) {
				//Claims 클래스는 Jwt 속성 정보를 가지는 클래스.
				Claims claims = Jwts.claims().setSubject(id);
				LocalDateTime now = LocalDateTime.now();
				return Jwts.builder()
						.setClaims(claims)	//데이터
						.setIssuedAt(Date.from(now.atZone(ZoneId.of("Asia/Seoul")).toInstant())) //토큰 발생 일자
						.setExpiration(Date.from(now.plusSeconds(7200).atZone(ZoneId.of("Asia/Seoul")).toInstant()))
//						.setExpiration(new Date(now.getTime()+accessTokenValidMilisecond)) //만료일자. 현재시간+유효시간
						.signWith(SignatureAlgorithm.HS256, secretKey)//시그니처. 알고리즘+시크릿키
						.compact();
	}
	
	//JWT 토큰으로 인증 정보를 조회
	public Authentication getAuthentication(String token) {
		// getUserId 메서드로 토큰으로부터 userId를 받고 loadUserByUsername으로 회원 정보 조회
		UserDetails userDetails = userDetailService.loadUserByUsername(this.getId(token));
		
		// UserDetails 객체와 권한을 바탕으로 UsernamePasswordAuthenticationToken 만들어 리턴
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	//JWT토큰에서 회원 구별 정보(userId) 추출
	public String getId(String token) {
		//받은 토큰을 시크릿키로 파싱하여 subject에 넣어둔 id값 꺼내오기
		String userId = Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(token).getBody().getSubject();
		return userId;
	}
	
	//Request의 Header에서 AccessToken을 파싱
	public String resolveAccessToken(HttpServletRequest request) {	
		return request.getHeader("AccessToken");
	}
	
	//Request의 Header에서 RefreshToken을 파싱
	public String resolveRefreshToken(HttpServletRequest request) {
		return request.getHeader("RefreshToken");
	}
	
	
	//AccessToken 토큰의 유효성 확인
	public boolean validateAccessToken(HttpServletResponse response,  String token) throws IOException {
		try { 																	//파싱 과정에서 jwt모듈이 알아서 예외를 발생 시킴. 
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch (ExpiredJwtException e) { 					//만료된 토큰일 경우
			makeResponse(ErrorCode.EXPIRED_TOKEN, response);
		}catch (UnsupportedJwtException  				 //지원하지 않는 JWT
							| MalformedJwtException			 //잘못된 JWT 서명
							| IllegalArgumentException e) {	 //잘못된 토큰일 경우
			makeResponse(ErrorCode.INVALID_TOKEN, response);
		} catch(Exception e) {
			makeResponse(ErrorCode.INVALID_TOKEN, response);
		}
		return false;
	}
	
	private void makeResponse(ErrorCode error, HttpServletResponse response) throws IOException {
		JsonObject json = new JsonObject();
		json.addProperty("code", error.getCode());
		json.addProperty("message", error.getMessage());
		PrintWriter out =  response.getWriter();
		out.print(json);
	}
	
	//Refresh 토큰의 유효성 확인
	public boolean validateRefreshToken(String token) {
		try {
			//파싱 과정에서 jwt모듈이 알아서 예외를 발생 시킴. 
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch(Exception e) {
			//어떤 exception이든 로그인 강제
			System.out.println("리프레시 토큰 만료");
			return false;
		}
	}


}
