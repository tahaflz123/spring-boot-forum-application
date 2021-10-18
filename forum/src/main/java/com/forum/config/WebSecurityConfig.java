package com.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.forum.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurityConfig(UserService userService ,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {

		  http
		  .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
	      .csrf().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
	      .authorizeRequests()
	         .antMatchers("/api/login").permitAll()
	         .antMatchers("/api/admin/**").hasAuthority("ADMIN")
	         .antMatchers("/api/v1/registration").permitAll()
	      .anyRequest().permitAll()
	      .and()
	      .cors();        
                /*.formLogin()
                   .loginPage("/login")
                   .permitAll()
                   .passwordParameter("password")
                   .usernameParameter("username")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) 
                    .key("somethingverysecured")
                    .rememberMeParameter("remember-me")
                    .and()
                .logout()
                   .logoutUrl("/logout")
                   .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                   .clearAuthentication(true)
                   .invalidateHttpSession(true)
                   .deleteCookies("JSESSIONID")
                   .logoutSuccessUrl("/login");*/
    }
	@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(this.bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
	@Bean
    public TokenFilter tokenFilter() {
    	return new TokenFilter();
    }
	
	
	
    

}