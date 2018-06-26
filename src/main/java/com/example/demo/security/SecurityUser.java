package com.example.demo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.demo.domain.Member;
import com.example.demo.domain.MemberRole;

public class SecurityUser extends User{
	private static final String ROLE_PREFIX = "ROLE_";
	
	private Member member;
	
	public SecurityUser(Member member) {
		super(member.getUid(), "{noop}"+member.getUpw(), makeGrantedAuthority(member.getRoles()));
		this.member = member;
	}
	
	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles){
		List<GrantedAuthority> list = new ArrayList<>();
		
		roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
		
		return list;
	}
}
