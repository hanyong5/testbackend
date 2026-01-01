package com.study.spring.member;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.study.spring.member.entity.Member;
import com.study.spring.member.entity.MemberRole;
import com.study.spring.member.repoository.MemberRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
//	@Test
//	public void testInsertMember() {
//		for (int i = 0; i < 10; i++) {
//			Member member = Member.builder()
//					.email("user" + i + "@naver.com")
//					.pw(passwordEncoder.encode("1111"))
//					.nickname("user" + i)
//					.build();
//			
//			member.addRole(MemberRole.USER);
//
//			if (i >= 5) {
//				member.addRole(MemberRole.MANAGER);
//			}
//
//			if (i >= 8) {
//				member.addRole(MemberRole.ADMIN);
//			}
//			memberRepository.save(member);
//
//		}
//	}
	
	
	@Test
	public void testRead() {
		String email = "user1@naver.com";
//		Optional<Member> member = memberRepository.findByEmail(email);

	    log.info("-----------------");
//	    log.info(member);
	}
}
