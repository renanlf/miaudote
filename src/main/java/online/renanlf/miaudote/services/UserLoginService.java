package online.renanlf.miaudote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.renanlf.miaudote.exceptions.EmailNotFoundException;
import online.renanlf.miaudote.model.UserLogin;
import online.renanlf.miaudote.repositories.UserLoginRepository;

@Service
public class UserLoginService {
	
	@Autowired
	private UserLoginRepository repo;
	
	public UserLogin findByEmail(String email) {
		return repo.findByEmail(email)
				.orElseThrow(() -> new EmailNotFoundException(email));
	}

	public UserLogin findByEmailAndPassword(String email, String password) {
		return repo.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new EmailNotFoundException(email));
	}

	public UserLogin save(UserLogin user) {
		return repo.save(user);
	}

}
