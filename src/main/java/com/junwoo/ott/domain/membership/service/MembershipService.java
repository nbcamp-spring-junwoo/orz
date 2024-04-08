package com.junwoo.ott.domain.membership.service;

import com.junwoo.ott.domain.membership.dto.request.CreateMembershipRequestDto;
import com.junwoo.ott.domain.membership.dto.response.MemberShipResponseDto;
import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.domain.membership.repository.MembershipRepository;
import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.exception.custom.AuthorityErrorException;
import com.junwoo.ott.global.exception.custom.NotExistMembershipTypeException;
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
        .orElseThrow(() -> new NotExistMembershipTypeException("Invalid membership id"))
        .toResponseDto();
  }

  public void createMembership(CreateMembershipRequestDto createMembershipRequestDto) {
    if (createMembershipRequestDto.getAuthority() != AuthorityType.ROLE_ADMIN) {
      throw new AuthorityErrorException("관리자 권한 필요");
    }

    membershipRepository.save(new Membership(createMembershipRequestDto.getMembershipType(),
        createMembershipRequestDto.getPrice()));
  }

}
