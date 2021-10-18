package com.forum.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.forum.dto.PostResponse;
import com.forum.entity.Post;
import com.forum.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    Post findByIdAndDeleted(Long id, boolean b);

	List<Post> findAllByCategoryIdAndDeleted(Long categoryId, boolean b);

	List<Post> findAllByDeleted(boolean b);
	

	@Query("select u from Post u where u.deleted = false and u.header like %?1%")
	List<Post> findByDeletedFalseAndHeaderLike(String q,Pageable pageable);

	List<Post> findAllByUser(User user);

	List<Post> findAllByDeletedAndUser(boolean b, User user);

	List<Post> findAllByZPointGreaterThan(Integer i);

	List<Post> findAllByZPointLessThan(Integer i);

	List<Post> findByZPointGreaterThan(Integer i);

	List<Post> findAllByDeletedFalseAndUser(User user);

	List<Post> findByPostDateBefore(Date date);

	List<Post> findByPostDateAfter(Date from);

	List<Post> findByDeletedFalseAndPostDateAfter(Date from);

	List<Post> findAllByDeletedFalseAndCategoryIdAndPostDateAfter(Long categoryId, Date date);

	List<Post> findAllByDeletedFalseAndCategoryId(Long categoryId);

	Page<Post> findAllByDeletedFalseAndPostDateAfter(Date date, Pageable pageable);

	Page<Post> findByDeletedFalseAndPostDateAfter(Date from, Pageable pageable);

	Page<Post> findAllByDeletedFalseAndUser(User user2, Pageable pageable);

	Page<Post> findAllByDeletedFalseAndCategoryId(Long categoryId, Pageable pageable);

	Page<Post> findAllByDeletedFalseAndCategoryIdAndPostDateAfter(Long categoryId, Date date, Pageable pageable);



}
