package com.egu.boot.BoardGame.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//로그인 패스워드
	@JsonProperty(access = Access.WRITE_ONLY)	//json출력 결과에서 제외
	@Column(length = 300)
	private String password;
	
	//로그인 아이디 - 이메일
	@Column(length = 200, unique = true, nullable=false)
	private String userId;
	
	@Column(length = 50, nullable = false)
	private String username;
	
	@Column
	private LocalDateTime createDate;
	
	@Column(length = 30, nullable = false)
	private String phoneNumber;
	
	private String provider;
	
	private String provider_id;

	private boolean isRegisterd;
	
//	@Enumerated(EnumType.STRING)
//	private RoleType role;

	//ORM에는 collection단위로 저장할 수 없는데,
	//@ElementCollection 어노테이션을 통해 Collection임을 알려주는 동시에
	//user_roles 테이블이 만들어진다.
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();
	
	private String refreshToken;
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
