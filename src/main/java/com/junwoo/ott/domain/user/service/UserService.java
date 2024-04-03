package com.junwoo.ott.domain.user.service;

import com.junwoo.ott.domain.auth.dto.request.AuthLoginRequestDto;
import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.dto.reponse.UserReadResponseDto;
import com.junwoo.ott.domain.user.dto.request.UserPutRequestDto;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.repository.UserRepository;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.PasswordNotEqualsException;
import com.junwoo.ott.global.exception.custom.UserNotFoundException;
import com.junwoo.ott.global.exception.custom.UserNotSameException;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public void createUser(final AuthSignupRequestDto authSignupRequestDto) {
    String encodedPassword = passwordEncoder.encode(authSignupRequestDto.getPassword());
    LocalDate datedBorn = LocalDate.parse(authSignupRequestDto.getBorn());
    User user = authSignupRequestDto.authSignupRequestDtoToUser(encodedPassword, datedBorn);

    userRepository.save(user);
  }

  public Authentication login(final AuthLoginRequestDto authLoginRequestDto) {
    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authLoginRequestDto.getUsername(), authLoginRequestDto.getPassword())
    );
  }

  @Transactional(readOnly = true)
  public UserReadResponseDto getUser(final Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."))
        .toReadResponseDto();
  }

  @Transactional(readOnly = true)
  public void validateUserNotExist(final String username) {
    if (userRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistException("이미 존재하는 username입니다.");
    }
  }

  public void putUser(final UserPutRequestDto userPutRequestDto) {
    Long requestId = userPutRequestDto.getRequestId();
    validateUser(requestId, userPutRequestDto.getUserId());
    User user = userRepository.findById(requestId).orElseThrow(
        () -> new UserNotFoundException("존재하지 않는 회원입니다.")
    );

    validatePassword(userPutRequestDto.getCheckedPassword(), user);

    updateUserInfo(userPutRequestDto, user);
  }

  private void validateUser(final Long requestId, final Long userId) {
    if (!requestId.equals(userId)) {
      throw new UserNotSameException("해당 회원정보에 대한 수정권한이 없습니다.");
    }
  }

  private void validatePassword(final String checkedPassword, final User user) {
    if (!passwordEncoder.matches(checkedPassword, user.getPassword())) {
      throw new PasswordNotEqualsException("비밀번호 불일치 수정 불가");
    }
  }

  private void updateUserInfo(final UserPutRequestDto userPutRequestDto, final User user) {
    String newPassword = userPutRequestDto.getNewPassword();
    String newEmail = userPutRequestDto.getNewEmail();
    String newBorn = userPutRequestDto.getNewBorn();

    if (newPassword != null) {
      String encodedNewPassword = passwordEncoder.encode(newPassword);
      user.updatePassword(encodedNewPassword);
    }

    if (newEmail != null) {
      user.updateEmail(newEmail);
    }

    if (newBorn != null) {
      LocalDate newDatedBorn = LocalDate.parse(userPutRequestDto.getNewBorn());
      user.updateBorn(newDatedBorn);
    }
  }

  public void updateUserMembership(
      final Long userid,
      final MembershipType membership
  ) {
    User user = userRepository.findById(userid).orElseThrow(
        () -> new UserNotFoundException("존재하지 않는 회원입니다.")
    );

    user.updateMembership(membership);
  }

}
