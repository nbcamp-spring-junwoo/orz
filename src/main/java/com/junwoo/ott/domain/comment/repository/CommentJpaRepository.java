package com.junwoo.ott.domain.comment.repository;

import com.junwoo.ott.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

}
