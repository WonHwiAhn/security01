package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.domain.Member;

public interface MemberRepostory extends CrudRepository<Member, String>{

}
