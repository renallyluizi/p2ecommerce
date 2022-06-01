package br.edu.unifacisa.ecommerce.sevice;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifacisa.ecommerce.exceptions.AuthenticationFailException;
import br.edu.unifacisa.ecommerce.model.AuthenticationToken;
import br.edu.unifacisa.ecommerce.model.User;
import br.edu.unifacisa.ecommerce.repository.TokenRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	TokenRepository tokenRepository;
	
	public void saveConfirmationToken(AuthenticationToken authenticationToken) {
		tokenRepository.save(authenticationToken);
	}
	
	public AuthenticationToken getToken(User user) {
		return tokenRepository.findByUser(user);
	}
		
	public User getUser(String token) {
		AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
		if (Objects.isNull(token)) {
			return null;
		}return authenticationToken.getUser();
		
	}
	
	public void authenticate(String token) {
		if (Objects.nonNull(token)) {
			throw new AuthenticationFailException("token not present");
		}
		if (Objects.isNull(getUser(token))){
			throw new AuthenticationFailException("token not valid");
		}
	}

}
