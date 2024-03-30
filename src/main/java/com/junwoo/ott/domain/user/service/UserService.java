package com.junwoo.ott.domain.user.service;

import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.dto.reponse.UserReadResponseDto;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.repository.UserRepository;
import com.junwoo.ott.global.exception.custom.UserNotFoundException;
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
    LocalDate datedBorn = LocalDate.parse(authSignupRequestDto.getBorn());
    User user = authSignupRequestDto.authSignupRequestDtoToUser(encodedPassword, datedBorn);

    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public UserReadResponseDto getUser(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."))
        .toReadResponseDto();
  }

  @Transactional(readOnly = true)
  public void validateUserNotExist(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistException("이미 존재하는 username입니다.");
    }
  }

}
