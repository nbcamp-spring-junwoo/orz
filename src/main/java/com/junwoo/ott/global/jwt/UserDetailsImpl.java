package com.junwoo.ott.global.jwt;

import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserDetailsImpl implements UserDetails {

  private final User user;

  public UserDetailsImpl(final User user) {
    this.user = user;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    AuthorityType authorityType = user.getAuthorityType();
    MembershipType membershipType = user.getMembershipType();

    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authorityType.getAuthority());
    SimpleGrantedAuthority membership = new SimpleGrantedAuthority(membershipType.getMembership());
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(authority);
    authorities.add(membership);

    return authorities;
  }

  public Long getUserId() {
    return user.getUserId();
  }

  public AuthorityType getAuthorityType() {
    return user.getAuthorityType();
  }

  public MembershipType getMembershipType() {
    return user.getMembershipType();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
