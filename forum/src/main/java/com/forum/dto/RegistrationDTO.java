package com.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class RegistrationDTO {
	    private String email;
	    private String username;
	    private String password;

	    public RegistrationDTO(String email, String username, String password) {
			this.email = email;
			this.username = username;
			this.password = password;
		}
		
	    
}
