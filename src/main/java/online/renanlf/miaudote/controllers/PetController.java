package online.renanlf.miaudote.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import online.renanlf.miaudote.model.Pet;
import online.renanlf.miaudote.model.PetShelter;
import online.renanlf.miaudote.model.PetView;
import online.renanlf.miaudote.model.Specie;
import online.renanlf.miaudote.services.PetService;
import online.renanlf.miaudote.services.PetShelterService;

@RestController
@RequestMapping("/pets")
public class PetController extends ErrorHandler {
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private PetShelterService shelterService;
	
	@GetMapping
	public List<PetView> getAll(
			@RequestParam Optional<String> labels,
			@RequestParam Optional<Float> maxAge,
			@RequestParam Optional<Float> minAge,
			@RequestParam Optional<Boolean> male,
			@RequestParam Optional<Specie> specie,
			@RequestParam Optional<Integer> shelterId 
		) {
		
		return petService.findAll(shelterId, labels, minAge, maxAge, male, specie);
	}
	
	@PostMapping
	public Pet postPet(@Valid @RequestBody Pet pet) {
		var shelter = getLoggedShelter();		
		pet.setShelter(shelter);
		
		return petService.save(pet);
	}
	
	@GetMapping("/{id}")
	public Pet getPetById(@PathVariable Long id) {
		assertAuthorization(getLoggedShelter(), id);
		
		return petService.findById(id);
	}
	
	@PutMapping("/{id}")
	public Pet putPet(@PathVariable Long id, @Valid @RequestBody Pet newPet) {
		var shelter = getLoggedShelter();
		newPet.setShelter(shelter);
		
		assertAuthorization(shelter, id);
		
		return petService.update(id, newPet);
	}
	
	@DeleteMapping("/{id}")
	public void deletePet(@PathVariable Long id) {
		assertAuthorization(getLoggedShelter(), id);
		
		petService.deleteById(id);
	}
	
	private PetShelter getLoggedShelter() {
		var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		return shelterService.findByEmail(email);
	}
	
	/**
	 * this method ensure there is no pet of that shelter with the given id
	 * @param shelter
	 * @param petId
	 */
	private void assertAuthorization(PetShelter shelter, Long petId) {
		if(shelter.getPets().stream()
				.allMatch(pet -> pet.getId() != petId)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

}
