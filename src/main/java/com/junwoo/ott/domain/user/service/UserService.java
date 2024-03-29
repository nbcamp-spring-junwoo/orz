package com.junwoo.ott.domain.user.service;

import com.junwoo.ott.domain.auth.dto.reponse.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.repository.UserRepository;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public void createUser(AuthSignupRequestDto authSignupRequestDto) {

    String encodedPassword = passwordEncoder.encode(authSignupRequestDto.getPassword());

    User user = User.builder()
        .username(authSignupRequestDto.getUsername())
        .password(encodedPassword)
        .email(authSignupRequestDto.getEmail())
        .born(LocalDate.parse(authSignupRequestDto.getBorn()))
        .build();

    userRepository.save(user);
  }

  public void validateUserNotExist(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistException("이미 존재하는 username입니다.");
    }
  }

}
