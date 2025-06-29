package com.nit.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nit.Service.UserServiceimpl;

@EnableWebSecurity
@Configuration
public class SecurityConfig{
	
	@Autowired
	public UserDetailsService userDetailsService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;
	
	@Autowired
	public BCryptPasswordEncoder pwdEncoder;
	
	  @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(userDetailsService);
	        provider.setPasswordEncoder(pwdEncoder);
	        return provider;
	    }
	  
	  @Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
			 http.authorizeHttpRequests(auth->auth
					.requestMatchers("/home,/reg, /save,/weblogin,/login").permitAll()
					.requestMatchers("/welcome").authenticated()
					.requestMatchers("/admin").hasAuthority("ADMIN")
					.requestMatchers("/emp").hasAuthority("EMPLOYEE")
					.requestMatchers("/student").hasAuthority("STUDENT")
					.requestMatchers("/common").hasAnyAuthority("EMPLOYEE","STUDENT")
					.anyRequest().permitAll()
					 )
			 
			 .formLogin()
			 .loginPage("/weblogin")
			 .loginProcessingUrl("/login")
			 .successHandler(successHandler)
			// .defaultSuccessUrl("/welcome", true)
			 .failureUrl("/weblogin?error=true")
			 
			 .and()
			 .logout()
			 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			 .logoutSuccessUrl("/weblogin?success=true")
			 
			 
			 .and()
			 .exceptionHandling()
			 .accessDeniedPage("/denied");
			 
			 
			return http.build();
		}


}
