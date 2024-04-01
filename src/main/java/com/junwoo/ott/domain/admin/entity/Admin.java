package com.junwoo.ott.domain.admin.entity;

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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update admin set deleted_at = NOW() where admin_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Entity
public class Admin extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long adminId;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String password;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private AuthorityType authorityType = AuthorityType.ROLE_ADMIN;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private MembershipType membershipType = MembershipType.ROLE_GOLD;
  private LocalDateTime deletedAt;

}
