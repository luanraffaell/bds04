package com.lrcs.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lrcs.models.Role;
import com.lrcs.models.User;
import com.lrcs.models.DTO.UserDTO;
import com.lrcs.repositories.RoleRepository;
import com.lrcs.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<UserDTO> findAll(){
		return userRepository.findAll().stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public UserDTO insert(UserDTO userDTO) {
		User user = null;
		user = dtoToUser(userDTO);		
		return new UserDTO(userRepository.save(user));
	}
	
	private User dtoToUser(UserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		String[] roles = userDTO.getRoles().split(",");
		for(String role : roles) {
			Role roleAuthority = roleRepository.findByAuthority(role);
			if(roleAuthority != null) {
				user.addRole(roleAuthority);
			}
		}
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username){
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
	
}
