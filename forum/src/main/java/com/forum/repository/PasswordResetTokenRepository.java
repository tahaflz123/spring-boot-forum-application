package com.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forum.entity.PasswordResetToken;
import com.forum.entity.User;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

	PasswordResetToken findByToken(String token);

	boolean existsByUser(User user);

	void deleteAllByUser(User user);

}
