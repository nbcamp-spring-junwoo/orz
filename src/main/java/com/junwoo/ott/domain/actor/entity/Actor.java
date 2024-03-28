package com.junwoo.ott.domain.actor.entity;

import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.SexType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Date;
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
@SQLDelete(sql = "UPDATE actor SET deleted_at = NOW() WHERE actor_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Entity
public class Actor extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long actorId;
  @Column(nullable = false)
  private String name;
  private Date bron;
  @Enumerated(EnumType.STRING)
  private SexType sex;

}
