package com.study.spring.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study.spring.member.dto.MemberDto;
import com.study.spring.member.entity.Member;
import com.study.spring.member.repoository.MemberRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("-----------loadUserByUsername 호출됨, username: {} ---------------", username);
		
		Optional<Member> member = memberRepository.findByEmail(username); 
		
		log.info("이메일 조회 결과 - username: {}, member.isPresent(): {}", username, member.isPresent());
		
		if(member.isEmpty()) {
			log.warn("사용자를 찾을 수 없습니다. username: {}", username);
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다. " + username);
		}
		
		
		
		System.out.println("정확한 정보임" + member.toString());
		
		MemberDto memberDto = new MemberDto(
				member.get().getEmail(),
				member.get().getPw(),
		        member.get().getNickname(),
		        member.get().isSocial(),
		        member.get().getMemberRoleList()
		            .stream()
		            .map(memberRole -> memberRole.name())
		            .collect(Collectors.toList())
				);
//	    System.out.println(memberDto.toString());
		
//	 MemberDto memberDto = new MemberDto(
//			 
//	            member.getEmail(),
//	            member.getPw(),
//	            member.getNickname(),
//	            member.isSocial(),
//	            member.getMemberRoleList()
//	                  .stream()
//	                  .map(memberRole -> memberRole.name()).collect(Collectors.toList()));
//


//	 System.out.println(memberDto.toString());
	 log.info(memberDto);		
		
		
		return memberDto;
	}

}
