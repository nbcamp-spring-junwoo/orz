package com.junwoo.ott.global.jwt;

import com.junwoo.ott.domain.admin.entity.Admin;
import com.junwoo.ott.domain.admin.repository.AdminRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.repository.UserRepository;
import com.junwoo.ott.global.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final AdminRepository adminRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    User user = null;

    if (username.startsWith("admin_")) {
      Admin admin = adminRepository.findByUsername(username)
          .orElseThrow(() -> new UserNotFoundException("존재하지 않는 관리자입니다."));

      user = new User(admin);
    } else {
      user = userRepository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    return new UserDetailsImpl(user);
  }

}
