package com.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forum.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{

	boolean existsByReporterUserIdAndPostId(Long userId, Long postId);

	boolean existsByReporterUserIdAndUserId(Long userId, Long userId2);

	boolean existsByReporterUserIdAndCommentId(Long userId, Long commentId);

}
