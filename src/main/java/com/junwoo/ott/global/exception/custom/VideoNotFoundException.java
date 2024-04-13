package com.junwoo.ott.global.exception.custom;

public class VideoNotFoundException extends RuntimeException {

  public VideoNotFoundException(final String message) {
    super(message);
  }

}
