package com.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forum.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long>{

	Vote findByPostId(Long id);

	Vote findByCommentId(Long id);

	Vote findByUserId(Long userId);

	Vote findByUserIdAndPostId(Long userId, Long id);

	Vote findByUserIdAndCommentId(Long userId, Long id);

}
