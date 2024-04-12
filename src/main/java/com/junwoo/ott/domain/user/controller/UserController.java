package com.junwoo.ott.domain.user.controller;

import com.junwoo.ott.domain.user.dto.body.UserPutDto;
import com.junwoo.ott.domain.user.dto.request.UserGetKeyRequestDto;
import com.junwoo.ott.domain.user.dto.request.UserPutRequestDto;
import com.junwoo.ott.domain.user.dto.response.UserGetKeyResponseDto;
import com.junwoo.ott.domain.user.dto.response.UserReadResponseDto;
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

  @GetMapping("/me")
  public ResponseDto<UserReadResponseDto> getMe(@AuthenticationPrincipal final UserDetailsImpl userDetails) {
    return ResponseDto.ok(userService.getUser(userDetails.getUserId()));
  }

  @GetMapping("/{id}")
  public ResponseDto<UserReadResponseDto> getUser(@PathVariable("id") final Long id) {
    return ResponseDto.ok(userService.getUser(id));
  }

  @PutMapping("/{id}")
  public void putUser(
      @PathVariable final Long id, @AuthenticationPrincipal final UserDetailsImpl userDetails,
      @RequestBody @Validated final UserPutDto userPutDto
  ) {
    UserPutRequestDto userPutRequestDto = new UserPutRequestDto(id, userDetails.getUserId(),
        userPutDto);
    userService.putUser(userPutRequestDto);
  }

  @GetMapping("/me/key")
  public ResponseDto<UserGetKeyResponseDto> getKey(@AuthenticationPrincipal final UserDetailsImpl userDetails) {
    UserGetKeyRequestDto userGetKeyRequestDto = new UserGetKeyRequestDto(userDetails.getUserId());
    return ResponseDto.ok(userService.getKey(userGetKeyRequestDto));
  }

}
