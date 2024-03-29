package com.junwoo.ott.domain.user.entity;

import com.junwoo.ott.domain.user.dto.reponse.UserReadResponseDto;
import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.customenum.MembershipType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update user set deleted_at = NOW() where user_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Entity
public class User extends Timestamped implements OAuth2User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false, unique = true)
  private String email;
  private LocalDate born;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private AuthorityType authorityType = AuthorityType.USER;
  @Enumerated(EnumType.STRING)
  @Builder.Default
  @Column(nullable = false)
  private MembershipType membershipType = MembershipType.NORMAL;
  private LocalDateTime deletedAt;


  public UserReadResponseDto toReadResponseDto() {
    return UserReadResponseDto.builder()
        .userId(userId)
        .username(username)
        .email(email)
        .born(born)
        .authorityType(authorityType)
        .membershipType(membershipType)
        .createdAt(this.getCreatedAt())
        .updatedAt(this.getUpdatedAt())
        .build();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public <A> A getAttribute(String name) {
    return OAuth2User.super.getAttribute(name);
  }

}
