package com.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forum.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findAllByPostId(Long post_id);

	List<Comment> findAllByPostIdAndDeleted(Long post_id, boolean b);

	Page<Comment> findAllByPostIdAndDeletedFalse(Long post_id, Pageable pageable);

}
