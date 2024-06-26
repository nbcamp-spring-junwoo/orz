package com.junwoo.ott.domain.user.entity;

import com.junwoo.ott.domain.admin.entity.Admin;
import com.junwoo.ott.domain.user.dto.response.UserReadResponseDto;
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
import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
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
  @Column(nullable = false, unique = true)
  private String customerKey;
  @Column(nullable = false)
  private LocalDate born;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private AuthorityType authorityType = AuthorityType.ROLE_USER;
  @Enumerated(EnumType.STRING)
  @Builder.Default
  @Column(nullable = false)
  private MembershipType membershipType = MembershipType.ROLE_NORMAL;
  private LocalDateTime deletedAt;

  public User(
      final Long userId,
      final String username,
      final AuthorityType authorityType,
      final MembershipType membershipType
  ) {
    this.userId = userId;
    this.username = username;
    this.authorityType = authorityType;
    this.membershipType = membershipType;
  }

  public User(final Admin admin) {
    this.userId = admin.getAdminId();
    this.username = admin.getUsername();
    this.password = admin.getPassword();
    this.authorityType = admin.getAuthorityType();
    this.membershipType = admin.getMembershipType();
  }

  @PrePersist
  public void setCustomerKey() {
    this.customerKey = UUID.randomUUID().toString();
  }

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

  public void updatePassword(final String encodedNewPassword) {
    this.password = encodedNewPassword;
  }

  public void updateEmail(final String newEmail) {
    this.email = newEmail;
  }

  public void updateBorn(final LocalDate newDatedBorn) {
    this.born = newDatedBorn;
  }

  public void updateMembership(final MembershipType membership) {
    this.membershipType = membership;
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
  public <A> A getAttribute(final String name) {
    return OAuth2User.super.getAttribute(name);
  }

}
