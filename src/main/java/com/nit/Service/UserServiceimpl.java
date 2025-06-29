package com.nit.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nit.model.User;
import com.nit.repo.UserRepository;


@Service
public class UserServiceimpl implements UserService, UserDetailsService{
	
	@Autowired
	public UserRepository repo;

	@Override
	public String storeData(User uer) {
		return repo.save(uer).getUname();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("------------------------->"+username);
		  Optional<User> user = repo.findByUemail(username);
		  if(user.isEmpty()) {
			  throw new UsernameNotFoundException("User not found with email: " + username);
		  }
		  System.out.println("------------------------->"+user);
		  org.springframework.security.core.userdetails.User usr=new org.springframework.security.core.userdetails.User(
				  user.get().getUemail(), 
				  user.get().getUpwd(), 
				  user.get().getRole().stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet())
				  );
		  System.out.println("-------------------3------>"+user);
		return usr;
	}

}
