
package br.edu.unifacisa.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unifacisa.ecommerce.dto.ResponseDto;
import br.edu.unifacisa.ecommerce.dto.user.SignInDto;
import br.edu.unifacisa.ecommerce.dto.user.SignInResponseDto;
import br.edu.unifacisa.ecommerce.dto.user.SignupDto;
import br.edu.unifacisa.ecommerce.exceptions.AuthenticationFailException;
import br.edu.unifacisa.ecommerce.exceptions.CustomException;
import br.edu.unifacisa.ecommerce.model.User;
import br.edu.unifacisa.ecommerce.repository.UserRepository;
import br.edu.unifacisa.ecommerce.sevice.AuthenticationService;
import br.edu.unifacisa.ecommerce.sevice.UserService;

@RequestMapping("user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	UserService userService;

	@GetMapping("/all")
	public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
		authenticationService.authenticate(token);
		return userRepository.findAll();
	}

	@PostMapping("/signup")
	public ResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
		return userService.signUp(signupDto);
	}

	
	@PostMapping("/signIn")
	public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws CustomException {
		return userService.signIn(signInDto);
	}

}
