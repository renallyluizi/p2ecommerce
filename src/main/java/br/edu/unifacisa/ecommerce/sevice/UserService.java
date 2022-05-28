package br.edu.unifacisa.ecommerce.sevice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.edu.unifacisa.ecommerce.dto.ResponseDto;
import br.edu.unifacisa.ecommerce.dto.user.SignupDto;
import br.edu.unifacisa.ecommerce.exceptions.CustomException;
import br.edu.unifacisa.ecommerce.model.User;
import br.edu.unifacisa.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public ResponseDto signUp(SignupDto signupDto) {

		if (userRepository.findByEmail(signupDto.getEmail())) {

			throw new CustomException("user already exists");
		}

		String encryptedpassword = signupDto.getPassword();
		try {
			encryptedpassword = hashPassword(signupDto.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(),
				encryptedpassword);

		try {
			userRepository.save(user);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

		ResponseDto responseDto = new ResponseDto("success", " response");
		return responseDto;
	}

	private String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return hash;
	}

}
