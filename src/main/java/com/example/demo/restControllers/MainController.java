package com.example.demo.restControllers;



import com.example.demo.bucket.S3Bucket;
import com.example.demo.bucket.S3Service;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/")
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

	@PostMapping("/upload")
	@Transactional
	public void putFile(@RequestBody MultipartFile multipartFile, Long userId){
			String profileImageId = UUID.randomUUID().toString();
			User user = userRepository.findAllById(userId);
			try {
				s3Service.putObject(
						"bekscloud",
						"profile-images/%s/%s".formatted(user.getId(),profileImageId),
						multipartFile.getBytes()
				);
				user.setImgLink(profileImageId);
				userRepository.save(user);
				logger.info("success");
			} catch (IOException e) {
				throw new RuntimeException("failed to upload profile image", e);
			}
	}

	@GetMapping("/getfile")
	public byte[] getFileFromAws(@RequestParam Long userId) throws IOException{
		try {
			User user = userRepository.findAllById(userId);
			if(user.getImgLink() == null){
				logger.info("Success");
				byte[] ImageBytes =
						s3Service.getObject(
								"bekscloud", "profile-images/%s/%s"
										.formatted(user.getId(),
												user.getImgLink()));
				return ResponseEntity.ok()
						.contentType(MediaType.IMAGE_JPEG).body(ImageBytes).getBody();
			}
			return "You may only update".getBytes();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/getProfile")
	public User getProfile(){
		return userService.getCurrentSessionUser();
	}

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
