package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum GenreType {
  COMEDY("COMEDY"),
  ACTION("ACTION"),
  ROMANCE("ROMANCE"),
  FANTASY("FANTASY"),
  ADVENTURE("ADVENTURE"),
  THRILLER("THRILLER"),
  SPORTS("SPORTS"),
  WAR("WAR"),
  HORROR("HORROR"),
  DETECTIVE("DETECTIVE"),
  CYBERPUNK("CYBERPUNK"),
  METAFICTION("METAFICTION"),
  MYSTERY("MYSTERY"),
  MILITARY("MILITARY"),
  BLACK_COMEDY("BLACK_COMEDY"),
  SPACE_OPERA("SPACE_OPERA"),
  WESTERN("WESTERN"),
  SCIENCE_FICTION("SCIENCE_FICTION"),
  DOCUMENTARY("DOCUMENTARY")
  ;

  private final String genre;

  GenreType(String genre) {
    this.genre = genre;
  }

}
