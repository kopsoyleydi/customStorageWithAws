package com.example.demo.body;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class Upload {

	private Long userId;

	private MultipartFile multipartFile;

}
