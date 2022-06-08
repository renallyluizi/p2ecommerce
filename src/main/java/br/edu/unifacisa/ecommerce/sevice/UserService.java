package br.edu.unifacisa.ecommerce.sevice;

import static br.edu.unifacisa.ecommerce.config.MessageStrings.USER_CREATED;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifacisa.ecommerce.config.MessageStrings;
import br.edu.unifacisa.ecommerce.dto.ResponseDto;
import br.edu.unifacisa.ecommerce.dto.user.SignInDto;
import br.edu.unifacisa.ecommerce.dto.user.SignInResponseDto;
import br.edu.unifacisa.ecommerce.dto.user.SignupDto;
import br.edu.unifacisa.ecommerce.dto.user.UserCreateDto;
import br.edu.unifacisa.ecommerce.enums.ResponseStatus;
import br.edu.unifacisa.ecommerce.enums.Role;
import br.edu.unifacisa.ecommerce.exceptions.AuthenticationFailException;
import br.edu.unifacisa.ecommerce.exceptions.CustomException;
import br.edu.unifacisa.ecommerce.model.AuthenticationToken;
import br.edu.unifacisa.ecommerce.model.User;
import br.edu.unifacisa.ecommerce.repository.UserRepository;
import br.edu.unifacisa.ecommerce.utils.Helper;

@Service
public class UserService {

	   @Autowired
	    UserRepository userRepository;

	    @Autowired
	    AuthenticationService authenticationService;

	    Logger logger = LoggerFactory.getLogger(UserService.class);


	    public ResponseDto signUp(SignupDto signupDto)  throws CustomException {
	        
	        if (Helper.notNull(userRepository.findByEmail(signupDto.getEmail()))) {
	            
	            throw new CustomException("User already exists");
	        }
	       
	        String encryptedPassword = signupDto.getPassword();
	        try {
	            encryptedPassword = hashPassword(signupDto.getPassword());
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            logger.error("hashing password failed {}", e.getMessage());
	        }


	        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), Role.user, encryptedPassword );

	        User createdUser;
	        try {
	           
	            createdUser = userRepository.save(user);
	            
	            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
	            
	            authenticationService.saveConfirmationToken(authenticationToken);
	            
	            return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
	        } catch (Exception e) {
	            // handle signup erro
	            throw new CustomException(e.getMessage());
	        }
	    }

	    public SignInResponseDto signIn(SignInDto signInDto) throws CustomException {
	        // usuario pelo email
	        User user = userRepository.findByEmail(signInDto.getEmail());
	        if(!Helper.notNull(user)){
	            throw  new AuthenticationFailException("user not present");
	        }
	        try {
	            // check senha correta
	            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
	                // senha errada
	                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
	            }
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            logger.error("hashing password failed {}", e.getMessage());
	            throw new CustomException(e.getMessage());
	        }

	        AuthenticationToken token = authenticationService.getToken(user);

	        if(!Helper.notNull(token)) {
	            // tolen nao encontrado
	            throw new CustomException("token not present");
	        }

	        return new SignInResponseDto ("success", token.getToken());
	    }


	    String hashPassword(String password) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());
	        byte[] digest = md.digest();
	        String myHash = DatatypeConverter
	                .printHexBinary(digest).toUpperCase();
	        return myHash;
	    }

	    public ResponseDto createUser(String token, UserCreateDto userCreateDto) throws CustomException, AuthenticationFailException {
	        User creatingUser = authenticationService.getUser(token);
	        if (!canCrudUser(creatingUser.getRole())) {
	            // usuario n pode criar outro usuario
	            throw  new AuthenticationFailException(MessageStrings.USER_NOT_PERMITTED);
	        }
	        String encryptedPassword = userCreateDto.getPassword();
	        try {
	            encryptedPassword = hashPassword(userCreateDto.getPassword());
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            logger.error("hashing password failed {}", e.getMessage());
	        }

	        User user = new User(userCreateDto.getFirstName(), userCreateDto.getLastName(), userCreateDto.getEmail(), userCreateDto.getRole(), encryptedPassword );
	        User createdUser;
	        try {
	            createdUser = userRepository.save(user);
	            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
	            authenticationService.saveConfirmationToken(authenticationToken);
	            return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
	        } catch (Exception e) {
	            // handle user creation fail error
	            throw new CustomException(e.getMessage());
	        }

	    }

	    boolean canCrudUser(Role role) {
	        if (role == Role.admin || role == Role.manager) {
	            return true;
	        }
	        return false;
	    }

	    boolean canCrudUser(User userUpdating, Integer userIdBeingUpdated) {
	        Role role = userUpdating.getRole();
	       
	        if (role == Role.admin || role == Role.manager) {
	            return true;
	        }
	        
	        if (role == Role.user && userUpdating.getId() == userIdBeingUpdated) {
	            return true;
	        }
	        return false;
	    }
	}
