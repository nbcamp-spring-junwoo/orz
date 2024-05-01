package com.junwoo.ott.domain.authority.entity;

import com.junwoo.ott.domain.admin.entity.Admin;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AdminAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long adminAuthorityId;

  @ManyToOne
  private Admin admin;
  @ManyToOne
  private Authority authority;

}