package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum CategoryType {
  FILM("FILM"),
  DRAMA("DRAMA"),
  ANIME("ANIME"),
  ENTERTAINMENT("ENTERTAINMENT");

  private final String category;

  CategoryType(String category) {
    this.category = category;
  }

}
