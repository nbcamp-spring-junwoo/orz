package com.junwoo.ott.domain.membership.service;

import com.junwoo.ott.domain.membership.dto.response.MemberShipResponseDto;
import com.junwoo.ott.domain.membership.dto.response.MembershipReadResponseDto;
import com.junwoo.ott.domain.membership.repository.MembershipRepository;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.NotExistMembershipTypeException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MembershipService {

  private final MembershipRepository membershipRepository;

  @Transactional(readOnly = true)
  public MemberShipResponseDto getMembership(final Long membershipId) {
    return membershipRepository.findById(membershipId)
        .orElseThrow(() -> new NotExistMembershipTypeException("존재하지 않는 맴버십입니다."))
        .toResponseDto();
  }

  @Transactional(readOnly = true)
  public MemberShipResponseDto getMembershipByMembershipType(final MembershipType membershipType) {
    return membershipRepository.findByMembershipType(membershipType)
        .orElseThrow(() -> new NotExistMembershipTypeException("존재하지 않는 맴버십입니다."))
        .toResponseDto();
  }

  @Transactional(readOnly = true)
  public List<MembershipReadResponseDto> getMemberships() {
    return membershipRepository.findAll().stream().map(
        membership -> new MembershipReadResponseDto(membership.getMembershipType(),
            membership.getPrice(), membership.getDetail())).toList();
  }

}
