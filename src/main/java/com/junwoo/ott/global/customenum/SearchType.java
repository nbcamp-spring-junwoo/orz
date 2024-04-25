package com.junwoo.ott.global.customenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {
  TITLE("title"),
  DESCRIPTION("description");

  private final String type;

}
