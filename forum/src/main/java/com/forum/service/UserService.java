package com.forum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.forum.dto.PostResponse;
import com.forum.dto.PostResults;
import com.forum.dto.SearchDTO;
import com.forum.dto.UserDTO;
import com.forum.dto.UserResponse;
import com.forum.dto.UserResults;
import com.forum.entity.Post;
import com.forum.entity.User;
import com.forum.exception.RegistrationException;
import com.forum.repository.PostRepository;
import com.forum.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private PostService postService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PostRepository postRepository;
    private AuthService authService;
    
    

    @Autowired
    public UserService(UserRepository userRepository,PostService postService, PasswordEncoder passwordEncoder
    		,PostRepository postRepository,AuthService authService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.postService = postService;
		this.postRepository = postRepository; 
		this.authService = authService;
	}

	@Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
		User inDB = this.userRepository.findByEmail(email);

		if(inDB == null) {
			throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
		}
		
		return null;
    }

    public String signUpUser(User appUser) {

    	boolean email = this.userRepository.existsByEmailAndUsername(appUser.getEmail(),appUser.getUsername());

    	if(!email)
    	{

    		System.out.println(appUser.toString());
    		System.err.println(appUser.getUsername());
    		String encodedPassword = passwordEncoder
                    .encode(appUser.getPassword());

            appUser.setPassword(encodedPassword);

            this.userRepository.save(appUser);


            return "Kaydınız gerçekleşti."; 
          }
    	else {
    		throw new RegistrationException("Bu E-posta kullanılıyor.");
    	}
    	
        
    }

	public UserResponse getUserByUsername(String username) {
		User user = this.userRepository.findByUsername(username);
		if(user != null) {
			List<Post> posts = this.postRepository.findAllByDeletedAndUser(false,user);
			user.setPostCount(posts.size());
			user.setZPoint(this.userZPoint(posts));
			UserResponse userResponse = this.getUserInfos(user);
			return userResponse;
		}
		return null;
	}
	
	public UserResponse getUserInfos(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setPostCount(user.getPostCount());
		userResponse.setUserId(user.getUserId());
		userResponse.setUsername(user.getUsername());
		userResponse.setZ_point(user.getZPoint());
		return userResponse;
	}
	
	public Integer userZPoint(List<Post> posts) {
		Integer count = 0;
		for(Post post : posts) {
			count = count + post.getZPoint();
		}
		return count;
	}

	public SearchDTO search(String q) {
		Pageable pageable = PageRequest.of(0, 3);
		List<User> users = this.userRepository.findByUsernameLike(q,pageable);
		List<UserResults> userResults = new ArrayList<UserResults>();
		System.err.println(users.toString());
		for(User user : users) {
			UserResults userResult = new UserResults();
			userResult.setUser_id(user.getUserId());
			userResult.setUsername(user.getUsername());
			userResults.add(userResult);
		}
		List<PostResults> posts = this.postService.postSearch(q);
		System.err.println(posts.toString());
		SearchDTO results = new SearchDTO();
		results.setPosts(posts);
		results.setUsers(userResults);
		return results;
	}

	public String getUserNameWithToken() {
		System.out.println(this.authService.getUser().getUsername());
		return this.authService.getUser().getUsername().toString();
	}

}