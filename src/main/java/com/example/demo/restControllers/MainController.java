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
	@Transactional
	public void putFile(@RequestBody MultipartFile multipartFile, Long userId){
			String profileImageId = UUID.randomUUID().toString();
			User user = new User();
			try {
				s3Service.putObject(
						"bekscloud",
						"profile-images/%s/%s".formatted(userId,profileImageId),
						multipartFile.getBytes()
				);
				user.setCustomerImageId(profileImageId);
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
			logger.info("Success");
			byte[] ImageBytes =
					s3Service.getObject(
							"bekscloud", "profile-images/%s/%s"
									.formatted(user.getId(),
											user.getCustomerImageId()));
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(ImageBytes).getBody();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
