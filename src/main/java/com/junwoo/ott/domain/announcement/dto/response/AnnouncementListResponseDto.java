package com.junwoo.ott.domain.announcement.dto.response;

import com.junwoo.ott.domain.announcement.dto.body.AnnouncementReadDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnnouncementListResponseDto {

  private List<AnnouncementReadDto> announcementReadDtoList;

}
