package online.renanlf.miaudote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import online.renanlf.miaudote.model.LoginRequisition;
import online.renanlf.miaudote.model.UserLogin;

@Service
public class LoginService {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserLoginService userService;
	
	@Autowired
	private AuthenticationManager authManager;

	public String authenticate(@Valid LoginRequisition loginReq) {		
		var authToken = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginReq.getEmail(), loginReq.getPassword()));
		
		var token = jwtService.generateToken(loginReq);
		
		var user = (UserLogin) authToken.getPrincipal();
		
		user.setToken(token);
		userService.save(user);
		
		return token;
	}
	
	

}
