package br.edu.unifacisa.ecommerce.sevice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifacisa.ecommerce.dto.ResponseDto;
import br.edu.unifacisa.ecommerce.dto.user.SignInDto;
import br.edu.unifacisa.ecommerce.dto.user.SignInResponseDto;
import br.edu.unifacisa.ecommerce.dto.user.SignupDto;
import br.edu.unifacisa.ecommerce.exceptions.AuthenticationFailException;
import br.edu.unifacisa.ecommerce.exceptions.CustomException;
import br.edu.unifacisa.ecommerce.model.AuthenticationToken;
import br.edu.unifacisa.ecommerce.model.User;
import br.edu.unifacisa.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationService authenticationService;

	@Transactional
	public ResponseDto signUp(SignupDto signupDto) {

		if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {

			throw new CustomException("user already exists");
		}
//hash senha
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
//criando token
		
		final AuthenticationToken authenticationToken = new AuthenticationToken(user);
		
		authenticationService.saveConfirmationToken(authenticationToken);
		
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

	public SignInResponseDto signIn(SignInDto signInDto) {
		
		User user = userRepository.findByEmail(signInDto.getEmail());
		
		if (Objects.isNull(user)) {
			throw new AuthenticationFailException("user is not valid");
		}
		
		try {
			if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
				throw new AuthenticationFailException("wrong password");
			}
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		AuthenticationToken token = authenticationService.getToken(user);
		
		if (Objects.isNull(token)){
			throw new CustomException("token is not present");
		}
		
		return new SignInResponseDto("sucess", token.getToken());
	}	

}
