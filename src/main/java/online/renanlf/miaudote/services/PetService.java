package online.renanlf.miaudote.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import online.renanlf.miaudote.model.Pet;
import online.renanlf.miaudote.model.PetView;
import online.renanlf.miaudote.model.Specie;
import online.renanlf.miaudote.repositories.PetRepository;

@Service
public class PetService {
	
	private static final String ERROR_MESSAGE = "Pet is not found";
	
	@Autowired
	private PetRepository repo;

	public List<PetView> findAll(
			Optional<Integer> shelterId, 
			Optional<String> labels, 
			Optional<Float> minAge, 
			Optional<Float> maxAge, 
			Optional<Boolean> male, 
			Optional<Specie> specie) {
		
//		Optional<List<String>> labelIds = labels
//				.map(s -> Arrays.asList(s.split(",")));
//		
//		String where = buildWhere(
//				shelterId,
//				minAge,
//				maxAge,
//				male, 
//				specie);
//		
//		List<Pet> pets = labelIds
//				.map(ids -> ids.stream().collect(Collectors.joining(",")))
//				.map(strIds -> repo.findAll(where, strIds))
//				.orElse(repo.findAll(where));
		
		
		return repo.findAll().stream()
				.map(PetView::of)
				.toList();
	}
	
	private String buildWhere(
			Optional<Integer> shelterId,
			Optional<Float> minAge, 
			Optional<Float> maxAge, 
			Optional<Boolean> male, 
			Optional<Specie> specie) {
		
		List<String> filters = new ArrayList<>();
		filters.add("deleted = false");
		
		shelterId.ifPresent(v -> filters.add("shelter_id = " + v));
		minAge.ifPresent(v -> filters.add("age >= " + v));
		maxAge.ifPresent(v -> filters.add("age <= " + v));
		male.ifPresent(v -> filters.add("gender = " + v));
		specie.ifPresent(v -> filters.add("specie = " + v.name()));
		
		return filters.stream().collect(Collectors.joining(" AND "));
		
	}

	public Pet save(Pet pet) {
		return repo.save(pet);
	}

	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	public Pet update(Long id, Pet newPet) {
		return repo.findById(id).map(pet -> {
			pet.setName(newPet.getName());
			pet.setBio(newPet.getBio());
			pet.setGender(newPet.getGender());
			pet.setSpecie(newPet.getSpecie());
			pet.setAge(newPet.getAge());
			pet.setLabels(newPet.getLabels());
			
			return pet;
		}).orElseThrow(() -> 
			new EntityNotFoundException(ERROR_MESSAGE + ":" + id));
	}

	public Pet findById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> 
					new EntityNotFoundException(ERROR_MESSAGE + ":" + id));
	}

}
