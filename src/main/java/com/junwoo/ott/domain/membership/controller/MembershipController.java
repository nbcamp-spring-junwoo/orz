package com.junwoo.ott.domain.membership.controller;

import com.junwoo.ott.domain.membership.dto.response.MembershipReadResponseDto;
import com.junwoo.ott.domain.membership.service.MembershipService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/memberships")
@RestController
public class MembershipController {

  private final MembershipService membershipService;

  @GetMapping
  public ResponseDto<List<MembershipReadResponseDto>> getMemberships() {
    return ResponseDto.ok(membershipService.getMemberships());
  }

}
