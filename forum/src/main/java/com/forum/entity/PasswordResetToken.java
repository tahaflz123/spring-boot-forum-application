package com.forum.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "password_reset_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {
	
	@Id
	@SequenceGenerator(name = "password_reset_token_sequence",allocationSize = 1)
	@GeneratedValue(generator = "password_reset_token_sequence",strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;
	
	private Date expiryDate;

}
