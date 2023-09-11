package online.renanlf.miaudote.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import online.renanlf.miaudote.model.PetLiker;
import online.renanlf.miaudote.model.Role;
import online.renanlf.miaudote.repositories.PetLikerRepository;

@Service
public class PetLikerService {
	
	@Autowired
	private PetLikerRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final String ERROR_MESSAGE = "Pet liker not found";

	public List<PetLiker> findAll() {
		return repo.findAll();
	}

	public PetLiker findById(Long id) {
		return repo.findById(id)
			.orElseThrow(() -> 
				new EntityNotFoundException(ERROR_MESSAGE + ":" + id));
	}

	public PetLiker save(PetLiker liker) {
		liker.setRole(Role.LIKER);
		liker.setPassword(passwordEncoder.encode(liker.getPassword()));
		return repo.save(liker);
	}

	public PetLiker update(Long id, PetLiker newOne) {
		return repo.findById(id)
		.map(liker -> {
			liker.setBio(newOne.getBio());
			liker.setName(newOne.getName());
			liker.setBirthDate(newOne.getBirthDate());
			liker.setHasPreviousPets(newOne.isHasPreviousPets());
			liker.setPhoneNumber(newOne.getPhoneNumber());
			return repo.save(liker);
		})
		.orElseThrow(() -> 
				new EntityNotFoundException(ERROR_MESSAGE + ":" + id));

	}

	public void delete(Long id) {
		repo.findById(id)
		.ifPresent(shelter -> {
			repo.delete(shelter);
		});
	}
	
	
}
