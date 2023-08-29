package online.renanlf.miaudote.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import online.renanlf.miaudote.exceptions.EmailNotFoundException;
import online.renanlf.miaudote.model.PetShelter;
import online.renanlf.miaudote.model.Role;
import online.renanlf.miaudote.repositories.PetShelterRepository;

@Service
public class PetShelterService {
	@Autowired
	private PetShelterRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final String ERROR_MESSAGE = "Pet shelter not found";

	public List<PetShelter> findAll() {
		return repo.findAll();
	}

	public PetShelter findById(Long id) {
		return repo.findById(id)
			.orElseThrow(() -> 
				new EntityNotFoundException(ERROR_MESSAGE + ":" + id));
	}
	
	public PetShelter findByEmail(String email) {
		return repo.findByEmail(email)
			.orElseThrow(() -> 
				new EmailNotFoundException(email));
	}

	public PetShelter save(PetShelter shelter) {
		shelter.setRole(Role.SHELTER);
		shelter.setPassword(passwordEncoder.encode(shelter.getPassword()));
		return repo.save(shelter);
	}

	public PetShelter update(Long id, PetShelter newOne) {
		return repo.findById(id)
		.map(shelter -> {
			shelter.setBio(newOne.getBio());
			shelter.setPhoneNumber(newOne.getPhoneNumber());
			shelter.setName(newOne.getName());
			shelter.setPhoneNumber(newOne.getPhoneNumber());
			return repo.save(shelter);
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
