package com.forum.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.forum.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	User findByEmail(String email);
	
	
	boolean existsByEmail (String email);


	User findByUsername(String username);


	@Query("select u from User u where u.username like %?1%")
	List<User> findAllByUsernameLike(String query);


	boolean existsByEmailAndUsername(String email, String username);
	
	@Query("select u from User u where u.username like %?1%")
	List<User> findByUsernameLike(String username,Pageable pageable);
	

}