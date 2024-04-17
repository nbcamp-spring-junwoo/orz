package com.junwoo.ott.domain.announcement.entity;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.global.common.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@SQLDelete(sql = "update announcement set deleted_at = NOW() where announcement_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Entity
public class Announcement extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long announcementId;
  @OneToOne
  @JoinColumn(name = "coupon_id")
  private Coupon coupon;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String content;
  private LocalDateTime deletedAt;

  public void updateAnnouncement(final Coupon coupon, final String title, final String content) {
    this.coupon = (coupon == null) ? this.coupon : coupon;
    this.title = (title == null) ? this.title : title;
    this.content = (content == null) ? this.content : content;
  }

}
