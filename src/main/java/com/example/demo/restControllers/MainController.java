package com.example.demo.restControllers;



import com.example.demo.bucket.S3Bucket;
import com.example.demo.bucket.S3Service;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class.getName());
	@Autowired
	private S3Service s3Service;

	@Autowired
	private S3Bucket s3Buckets;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;


	@PostMapping(value = "/to-sign-up")
	public String toSignUp(@RequestParam(name = "user_email") String email,
						   @RequestParam(name = "user_password") String password,
						   @RequestParam(name = "user_repeat_password") String repeatPassword,
						   @RequestParam(name = "user_full_name") String fullName) {
		return userService.signUpService(email,password,repeatPassword,fullName);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/getCurrentUser/{id}")
	public User getCurrentSessionUser(@PathVariable(name = "id") Long id){
		return userService.getUserById(id);
	}

}
