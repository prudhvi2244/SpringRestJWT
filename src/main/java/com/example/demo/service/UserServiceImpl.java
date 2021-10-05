package com.example.demo.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String saveUser(User user) {
		String encodedPassword=passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User savedUser=urepo.save(user);
		Integer uid=savedUser.getUid();
		String msg="User With ID :"+uid+" Saved Successfully";
		return msg;
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opt=urepo.findByUsername(username);
		if(opt.isEmpty())
		{
			throw new UsernameNotFoundException("User Does Not Exist!");
		}
		else
		{
			User user=opt.get();
			return new org.springframework.security.core.userdetails.
										User(username,user.getPassword(),
												user.getRoles().stream().map(role->new SimpleGrantedAuthority(role))
												.collect(Collectors.toSet())
												);
											
		}
	}
	

}
