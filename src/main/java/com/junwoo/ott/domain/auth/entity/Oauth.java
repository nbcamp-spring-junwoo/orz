package com.junwoo.ott.domain.auth.entity;

import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.customenum.OauthType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Oauth {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String oauthId;
  @ManyToOne
  private User user;
  @Enumerated(EnumType.STRING)
  private OauthType oauthType;
  private String oauthEmail;

}
