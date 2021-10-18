package com.forum.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Entity
@Table(name = "app_user",uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{

	@Id
	@SequenceGenerator(name = "user_sequence")
	@GeneratedValue(generator = "user_sequence",strategy = GenerationType.SEQUENCE)
	private Long userId;

	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String username;
	
	@Enumerated(EnumType.STRING)
	@Column
	private UserRole appUserRole;
	
	//Like Count
	@Builder.Default
	private Integer zPoint = 0;
	
	@Builder.Default
	private Integer postCount = 0;
	
	@Column
	@Builder.Default
	private Boolean locked = false;
	
	@Column
	@Builder.Default
    private Boolean enabled = true;
	
	public UserRole getAppUserRole() {
		return appUserRole;
	}

	public void setAppUserRole(UserRole appUserRole) {
		this.appUserRole = appUserRole;
	}

	private String password;
	
	public User(String email, String password,String username, UserRole appUserRole) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.appUserRole = appUserRole;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 SimpleGrantedAuthority authority =
	                new SimpleGrantedAuthority(appUserRole.name());
	        return Collections.singletonList(authority);
	}


	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	
	
}
