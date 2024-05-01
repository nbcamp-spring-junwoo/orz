package com.junwoo.ott.domain.membership.entity;

import com.junwoo.ott.domain.user.entity.User;
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
public class UserMembership {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userAuthorityId;

  @ManyToOne
  private User user;
  @ManyToOne
  private Membership memberShip;

}