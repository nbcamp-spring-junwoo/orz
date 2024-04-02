package com.junwoo.ott.domain.user.controller;

import com.junwoo.ott.domain.user.dto.body.UserPutDto;
import com.junwoo.ott.domain.user.dto.reponse.UserReadResponseDto;
import com.junwoo.ott.domain.user.dto.request.UserPutRequestDto;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseDto<UserReadResponseDto> getUser(@PathVariable("id") final Long id) {
    return ResponseDto.ok(userService.getUser(id));
  }

  @PutMapping("/{id}")
  public void putUser(
      @PathVariable final Long id, @AuthenticationPrincipal final UserDetailsImpl userDetails,
      @RequestBody @Validated UserPutDto userPutDto
  ) {
    UserPutRequestDto userPutRequestDto = new UserPutRequestDto(id, userDetails.getUserId(),
        userPutDto);
    userService.putUser(userPutRequestDto);
  }

}
