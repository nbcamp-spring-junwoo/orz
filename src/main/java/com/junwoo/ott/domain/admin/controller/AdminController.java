package com.junwoo.ott.domain.admin.controller;

import com.junwoo.ott.domain.membership.dto.body.CreateMembershipDto;
import com.junwoo.ott.domain.membership.dto.request.CreateMembershipRequestDto;
import com.junwoo.ott.domain.membership.service.MembershipService;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {

  private final MembershipService membershipService;

  @PostMapping("/membership")
  public void createMembership(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody CreateMembershipDto createMembershipDto
  ) {
    CreateMembershipRequestDto createMembershipRequestDto = new CreateMembershipRequestDto
        (userDetails.getAuthorityType(), createMembershipDto);

    membershipService.createMembership(createMembershipRequestDto);
  }

}
