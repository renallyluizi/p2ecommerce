package br.edu.unifacisa.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unifacisa.ecommerce.dto.ResponseDto;
import br.edu.unifacisa.ecommerce.dto.user.SignupDto;
import br.edu.unifacisa.ecommerce.sevice.UserService;

@RequestMapping("user")
@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public ResponseDto signup(@RequestBody SignupDto signupDto) {
		return userService.signUp(signupDto);
	}
	
	
}
