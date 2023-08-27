package com.example.demo.restControllers;


import com.example.demo.bucket.S3Bucket;
import com.example.demo.bucket.S3Service;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("home/")
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
	public void putFile(@RequestBody MultipartFile file, Long userId){
			String profileImageId = UUID.randomUUID().toString();
			try {
				s3Service.putObject(
						"bekscloud",
						"profile-images/%s/%s".formatted(userId,profileImageId),
						file.getBytes()
				);
				logger.info("success");
			} catch (IOException e) {
				throw new RuntimeException("failed to upload profile image", e);
			}
	}

	@GetMapping("/getfile")
	public byte[] getFileFromAws(){
		try {
			User sessionUser = userService.getCurrentSessionUser();
			logger.info("Success");
			return s3Service.getObject("bekscloud", "profile-images/%s".formatted());
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
