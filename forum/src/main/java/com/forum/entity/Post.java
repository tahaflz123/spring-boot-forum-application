package com.forum.entity;


import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.Nullable;


@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

	@Id
	@SequenceGenerator(name = "post_sequence",allocationSize = 1)
	@GeneratedValue(generator = "post_sequence",strategy = GenerationType.SEQUENCE)
	@Column(unique = true)
	private Long id;
	
	@Builder.Default
	private Integer ZPoint = 0;

	@Builder.Default
	private Boolean deleted = false;
	
	@Column(nullable = false)
	private Long categoryId;
	
	private String header;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm")
	private Date postDate;
	
	@Lob
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	
}
