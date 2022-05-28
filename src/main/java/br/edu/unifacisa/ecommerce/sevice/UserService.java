package br.edu.unifacisa.ecommerce.sevice;

import org.springframework.stereotype.Service;

import dto.ResponseDto;
import dto.SignupDto;

@Service
public class UserService {

	public ResponseDto signUp(SignupDto signupDto) {
		ResponseDto responseDto = new ResponseDto("success", "dummy response");
		return responseDto; 
	}

}
