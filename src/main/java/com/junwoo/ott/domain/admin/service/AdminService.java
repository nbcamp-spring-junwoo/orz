package com.junwoo.ott.domain.admin.service;

import com.junwoo.ott.domain.admin.entity.Admin;
import com.junwoo.ott.domain.admin.repository.AdminRepository;
import com.junwoo.ott.domain.auth.dto.request.AuthAdminSignupRequestDto;
import com.junwoo.ott.domain.auth.dto.request.AuthLoginRequestDto;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

  private final AdminRepository adminRepository;

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Value("${admin.prefix}")
  private String adminPreFix;

  public void createAdmin(AuthAdminSignupRequestDto authAdminSignupRequestDto) {
    String username = authAdminSignupRequestDto.getUsername();
    validateAdminNotExist(username);
    String reformedAdminName = adminPreFix + username;
    String encodedPassword = passwordEncoder.encode(authAdminSignupRequestDto.getPassword());

    Admin admin = authAdminSignupRequestDto.authAdminSignupRequestDtoToAdmin(reformedAdminName,
        encodedPassword);
    adminRepository.save(admin);
  }

  public Authentication login(AuthLoginRequestDto authLoginRequestDto) {
    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authLoginRequestDto.getUsername(), authLoginRequestDto.getPassword())
    );
  }

  @Transactional(readOnly = true)
  public void validateAdminNotExist(String username) {
    if (adminRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistException("이미 존재하는 username입니다.");
    }
  }

}
