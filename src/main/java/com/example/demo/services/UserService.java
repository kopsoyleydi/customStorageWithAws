package com.example.demo.services;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repo.RoleRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public class UserService implements UserDetailsService {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	public User getCurrentSessionUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return (User) authentication.getPrincipal();
		}
		return null;
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return (UserDetails) user;
		} else {
			throw new UsernameNotFoundException("User Not found");
		}
	}

	public User addUser(User user) {
		User checkUser = userRepository.findByEmail(user.getEmail());
		if (checkUser == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		}
		return null;
	}

	public String signUpService(String email, String password, String repeatPassword, String name) {
		if (password.equals(repeatPassword)) {
			User user = new User();
			user.setEmail(email);
			user.setFullName(name);
			user.setPassword(password);
			user.setPermissions(roleRepository.findAllById(1L));
			User newUser = addUser(user);
			if (newUser != null) {
				return "redirect:/sign-up-page?success";
			} else {
				return "redirect:/sign-up-page?emailerror";
			}
		} else {
			return "redirect:/sign-up-page?passworderror";
		}
	}

	public User getUserById(Long id){
		return userRepository.findAllById(id);
	}
}
