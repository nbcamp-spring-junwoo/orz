package com.junwoo.ott.domain.coupon.dto.body;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
public class CouponCreateDto {

  private String description;
  @NotNull
  private String type;
  @NotNull
  private String membershipType;
  @NotNull
  private Integer discount;
  @NotNull
  private Integer count;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String startAt;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String endAt;

}
