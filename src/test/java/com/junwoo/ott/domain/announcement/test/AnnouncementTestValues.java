package com.junwoo.ott.domain.announcement.test;

import com.junwoo.ott.domain.announcement.dto.body.AnnouncementCreateDto;
import com.junwoo.ott.domain.announcement.dto.body.AnnouncementUpdateDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementCreateRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementDeleteRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementReadRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementUpdateRequestDto;
import com.junwoo.ott.domain.announcement.entity.Announcement;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.global.customenum.CouponType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public interface AnnouncementTestValues {

  Long TEST_ANNOUNCEMENT_ID_V1 = 1L;
  Long TEST_ANNOUNCEMENT_ID_V2 = 2L;
  Long TEST_ANNOUNCEMENT_ID_V3 = 3L;
  String TEST_ANNOUNCEMENT_TITLE_V1 = "Test Announcement Title1";
  String TEST_ANNOUNCEMENT_TITLE_V2 = "Test Announcement Title2";
  String TEST_ANNOUNCEMENT_TITLE_V3 = "Test Announcement Title3";
  String TEST_ANNOUNCEMENT_TEXT_V1 = "Test Announcement Text1";
  String TEST_ANNOUNCEMENT_TEXT_V2 = "Test Announcement Text2";
  String TEST_ANNOUNCEMENT_TEXT_V3 = "Test Announcement Text3";

  Long TEST_COUPON_ID_V1 = 1L;
  Long TEST_COUPON_ID_V2 = 2L;
  Long TEST_COUPON_ID_V3 = 3L;
  String TEST_COUPON_TITLE_V1 = "Test Coupon Title1";
  String TEST_COUPON_TITLE_V2 = "Test Coupon Title2";
  CouponType TEST_COUPON_TYPE_V1 = CouponType.FIX;
  CouponType TEST_COUPON_TYPE_V2 = CouponType.RATIO;
  MembershipType TEST_MEMBERSHIP_TYPE_V1 = MembershipType.ROLE_GOLD;
  MembershipType TEST_MEMBERSHIP_TYPE_V2 = MembershipType.ROLE_SILVER;
  Integer TEST_COUPON_COUNT_V1 = 100;
  Integer TEST_COUPON_COUNT_V2 = 200;
  Integer TEST_COUPON_DISCOUNT_V1 = 10;
  Integer TEST_COUPON_DISCOUNT_V2 = 20;
  LocalDate TEST_COUPON_START_DATE_V1 = LocalDate.of(2020, 1, 1);
  LocalDate TEST_COUPON_START_DATE_V2 = LocalDate.of(2020, 1, 2);
  LocalDate TEST_COUPON_END_DATE_V1 = LocalDate.of(2020, 2, 1);
  LocalDate TEST_COUPON_END_DATE_V2 = LocalDate.of(2020, 2, 2);

  Integer TEST_PAGE_NUMBER = 1;

  Coupon TEST_COUPON_V1 = Coupon.builder()
      .couponId(TEST_COUPON_ID_V1)
      .description(TEST_COUPON_TITLE_V1)
      .couponType(TEST_COUPON_TYPE_V1)
      .membershipType(TEST_MEMBERSHIP_TYPE_V1)
      .count(TEST_COUPON_COUNT_V1)
      .discount(TEST_COUPON_DISCOUNT_V1)
      .startAt(TEST_COUPON_START_DATE_V1)
      .endAt(TEST_COUPON_END_DATE_V1)
      .build();

  Coupon TEST_COUPON_V2 = Coupon.builder()
      .couponId(TEST_COUPON_ID_V2)
      .description(TEST_COUPON_TITLE_V2)
      .couponType(TEST_COUPON_TYPE_V2)
      .membershipType(TEST_MEMBERSHIP_TYPE_V2)
      .count(TEST_COUPON_COUNT_V2)
      .discount(TEST_COUPON_DISCOUNT_V2)
      .startAt(TEST_COUPON_START_DATE_V2)
      .endAt(TEST_COUPON_END_DATE_V2)
      .build();

  Coupon TEST_COUPON_V3 = Coupon.builder()
      .couponId(TEST_COUPON_ID_V3)
      .description(TEST_COUPON_TITLE_V1)
      .couponType(TEST_COUPON_TYPE_V1)
      .membershipType(TEST_MEMBERSHIP_TYPE_V1)
      .count(TEST_COUPON_COUNT_V1)
      .discount(TEST_COUPON_DISCOUNT_V1)
      .startAt(TEST_COUPON_START_DATE_V1)
      .endAt(TEST_COUPON_END_DATE_V1)
      .build();

  Announcement TEST_ANNOUNCEMENT_V1 = Announcement.builder()
      .announcementId(TEST_ANNOUNCEMENT_ID_V1)
      .title(TEST_ANNOUNCEMENT_TITLE_V1)
      .content(TEST_ANNOUNCEMENT_TEXT_V1)
      .coupon(TEST_COUPON_V1)
      .build();

  Announcement TEST_ANNOUNCEMENT_V2 = Announcement.builder()
      .announcementId(TEST_ANNOUNCEMENT_ID_V2)
      .title(TEST_ANNOUNCEMENT_TITLE_V2)
      .content(TEST_ANNOUNCEMENT_TEXT_V2)
      .coupon(TEST_COUPON_V2)
      .build();

  Announcement TEST_ANNOUNCEMENT_V3 = Announcement.builder()
      .announcementId(TEST_ANNOUNCEMENT_ID_V3)
      .title(TEST_ANNOUNCEMENT_TITLE_V3)
      .content(TEST_ANNOUNCEMENT_TEXT_V3)
      .coupon(TEST_COUPON_V3)
      .build();

  AnnouncementCreateDto TEST_ANNOUNCEMENT_CREATE_DTO_V1 = new AnnouncementCreateDto(
      TEST_COUPON_ID_V3,
      TEST_ANNOUNCEMENT_TITLE_V3, TEST_ANNOUNCEMENT_TEXT_V3);

  AnnouncementCreateRequestDto TEST_ANNOUNCEMENT_CREATE_REQUEST_DTO_V1 = new AnnouncementCreateRequestDto(
      TEST_ANNOUNCEMENT_CREATE_DTO_V1);

  AnnouncementUpdateDto TEST_ANNOUNCEMENT_UPDATE_DTO_V1 = new AnnouncementUpdateDto(
      TEST_COUPON_ID_V2, TEST_ANNOUNCEMENT_TITLE_V2, TEST_ANNOUNCEMENT_TEXT_V2);

  AnnouncementUpdateRequestDto TEST_ANNOUNCEMENT_UPDATE_REQUEST_DTO_V1 = new AnnouncementUpdateRequestDto(
      TEST_ANNOUNCEMENT_UPDATE_DTO_V1, TEST_ANNOUNCEMENT_ID_V1);

  AnnouncementDeleteRequestDto TEST_ANNOUNCEMENT_DELETE_REQUEST_DTO_V1 = new AnnouncementDeleteRequestDto(
      TEST_ANNOUNCEMENT_ID_V1);

  AnnouncementReadRequestDto TEST_ANNOUNCEMENT_READ_REQUEST_DTO_V1 = new AnnouncementReadRequestDto(
      TEST_ANNOUNCEMENT_ID_V1);

  Page<Announcement> TEST_ANNOUNCEMENT_LIST_V1 = new PageImpl<>(List.of(TEST_ANNOUNCEMENT_V1));

}
