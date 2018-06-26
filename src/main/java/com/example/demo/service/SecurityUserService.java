package com.example.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepostory;
import com.example.demo.security.SecurityUser;

import lombok.extern.java.Log;

@Service
@Log
public class SecurityUserService implements UserDetailsService{

	@Autowired
	MemberRepostory repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 아이디는 아무거나 입력하고 비번은 1111입력하면 로그인 가능
		// 권한은 MANAGER
		//User sampleUser = new User(username, "{noop}1111", Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")));
		
		// repo.findById(username).ifPresent(member -> System.out.println("유저정보:: " + member));
		System.out.println(repo.findById(username).filter(m->m != null).map(m -> new SecurityUser(m)).get());
		
		return repo.findById(username).filter(m->m != null).map(m -> new SecurityUser(m)).get();
	}

}
