package br.edu.unifacisa.ecommerce.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		

}
