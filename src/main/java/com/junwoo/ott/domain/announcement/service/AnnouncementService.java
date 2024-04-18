package com.junwoo.ott.domain.announcement.service;

import com.junwoo.ott.domain.announcement.dto.request.AnnouncementCreateRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementDeleteRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementReadRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementUpdateRequestDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementCreateResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementReadResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementUpdateResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementsReadResponseDto;
import com.junwoo.ott.domain.announcement.entity.Announcement;
import com.junwoo.ott.domain.announcement.repository.AnnouncementRepository;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.global.exception.custom.CustomAnnouncementException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AnnouncementService {

  private final CouponService couponService;

  private final AnnouncementRepository announcementRepository;

  private final int PAGE_SIZE = 10;

  @Transactional(readOnly = true)
  public AnnouncementReadResponseDto getAnnouncement(final AnnouncementReadRequestDto dto) {
    Announcement announcement = existAnnouncement(dto.getAnnouncementId());

    return new AnnouncementReadResponseDto(announcement);
  }

  @Transactional(readOnly = true)
  public Page<AnnouncementsReadResponseDto> getAnnouncementList(final int page) {
    if (page <= 0) {
      throw new CustomAnnouncementException("더이상 조회할 수 없습니다.");
    }

    Page<Announcement> announcements = announcementRepository.findAllByOrderByCreatedAtDesc(
        PageRequest.of(page - 1, PAGE_SIZE));

    if (announcements.getTotalElements() < (long) (page - 1) * PAGE_SIZE) {
      throw new CustomAnnouncementException("더이상 조회할 수 없습니다.");
    }

    return announcements.map(announcement ->
        new AnnouncementsReadResponseDto(announcement.getAnnouncementId(),
            announcement.getTitle()));
  }

  public AnnouncementCreateResponseDto createAnnouncement(
      final AnnouncementCreateRequestDto createRequestDto
  ) {
    Coupon coupon = null;

    if (createRequestDto.getCouponId() != null) {
      coupon = couponService.existCouponById(createRequestDto.getCouponId());
    }

    Announcement announcement = Announcement.builder()
        .title(createRequestDto.getTitle())
        .content(createRequestDto.getContent())
        .coupon(coupon)
        .build();

    Announcement savedAnnouncement = announcementRepository.save(announcement);
    return new AnnouncementCreateResponseDto(savedAnnouncement);
  }

  public AnnouncementUpdateResponseDto updateAnnouncement(
      final AnnouncementUpdateRequestDto updateRequestDto
  ) {
    Announcement announcement = existAnnouncement(updateRequestDto.getAnnouncementId());

    Coupon coupon = (updateRequestDto.getCouponId() == null) ? null : couponService.existCouponById(
        updateRequestDto.getCouponId());

    announcement.updateAnnouncement(coupon, updateRequestDto.getTitle(),
        updateRequestDto.getContent());

    return new AnnouncementUpdateResponseDto(announcement);
  }

  public void deleteAnnouncement(final AnnouncementDeleteRequestDto dto) {
    Announcement announcement = existAnnouncement(dto.getAnnouncementId());

    announcementRepository.delete(announcement);
  }

  private Announcement existAnnouncement(final Long announcementId) {
    return announcementRepository.findById(announcementId)
        .orElseThrow(() -> new EntityNotFoundException("해당 공지사항이 존재하지 않습니다."));
  }

}
