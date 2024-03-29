package com.junwoo.ott.domain.user.controller;

import com.junwoo.ott.domain.user.dto.reponse.UserReadResponseDto;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseDto<UserReadResponseDto> getUser(@PathVariable("id") Long id) {
    return ResponseDto.ok(userService.getUser(id));
  }

}
