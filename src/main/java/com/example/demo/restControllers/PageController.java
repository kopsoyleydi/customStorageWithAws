package com.example.demo.restControllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping(value = "/sign-in-page")
	public String signInPage(){
		return "sign-in";
	}

	@GetMapping(value = "/profile")
	public String profilePage(){
		return "profile";
	}

	@GetMapping(value = "403")
	public String error403(){
		return "403page";
	}

	@GetMapping(value = "/sign-up-page")
	public String signupPage() {
		return "signup";
	}

	@GetMapping(value = "/")
	public String main(){
		return "index";
	}
}
