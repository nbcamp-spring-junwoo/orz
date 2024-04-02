package com.junwoo.ott.domain.membership.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;

import com.junwoo.ott.domain.membership.dto.response.MemberShipResponseDto;
import com.junwoo.ott.domain.membership.repository.MembershipRepository;
import com.junwoo.ott.domain.membership.test.MembershipServiceTestValues;
import com.junwoo.ott.global.exception.custom.NotExistMembershipTypeException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest implements MembershipServiceTestValues {

  @InjectMocks
  private MembershipService membershipService;

  @Mock
  private MembershipRepository membershipRepository;

  @Nested
  class GetMembership {

    @DisplayName("성공")
    @Test
    void success() {
      // given
      given(membershipRepository.findById(anyLong())).willReturn(Optional.of(TEST_MEMBERSHIP));

      // when
      MemberShipResponseDto result = membershipService.getMembership(1L);

      // then
      assertNotNull(result);
      then(membershipRepository).should(times(1)).findById(anyLong());
    }

    @DisplayName("실패: 존재하지 않는 멤버십 타입")
    @Test
    void fail_NotExistMembershipType() {
      // given
      given(membershipRepository.findById(anyLong())).willReturn(Optional.empty());

      // when & then
      NotExistMembershipTypeException e = assertThrows(NotExistMembershipTypeException.class, () -> membershipService.getMembership(1L));
      assertTrue(e.getMessage().contains("Invalid membership id"));
    }

  }

}
