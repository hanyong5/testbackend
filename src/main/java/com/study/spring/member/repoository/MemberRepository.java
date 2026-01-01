package com.study.spring.member.repoository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.study.spring.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

	@Query("""
			select m from Member 
			m left join fetch m.memberRoleList 
			where m.email = :email
			""")
	Optional<Member> findByEmail(@Param("email") String email);

}
