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
import org.springframework.stereotype.Service;


public class UserService implements UserDetailsService {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;


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

	public User addUser(User user){
		return userRepository.save(user);
	}

	public String signUpService(String email, String password, String repeatPassword, String name) {
		if (password.equals(repeatPassword)) {
			User user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setPassword(password);
			user.setUserRole(roleRepository.findAllById(1L));
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
}
