package com.study.spring.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "memeberRoleList")
public class Member {
	@Id
	private String email;
	private String pw;
	private String nickname;
	private boolean social;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private List<MemberRole> memberRoleList = new ArrayList<>();
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	
	
	public void addRole(MemberRole memberRole) {
		memberRoleList.add(memberRole);
	}

	public void clearRole() {
		memberRoleList.clear();
	}

	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}

	public void changePw(String pw) {
		this.pw = pw;
	}

	public void changeSocial(boolean social) {
		this.social = social;
	}

	
	
}
