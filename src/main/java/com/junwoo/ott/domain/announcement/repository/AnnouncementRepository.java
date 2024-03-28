package com.junwoo.ott.domain.announcement.repository;

import com.junwoo.ott.domain.announcement.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

}
