package online.renanlf.miaudote.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import online.renanlf.miaudote.model.Gender;
import online.renanlf.miaudote.model.Pet;
import online.renanlf.miaudote.model.PetView;
import online.renanlf.miaudote.model.Specie;
import online.renanlf.miaudote.repositories.PetRepository;

@Service
public class PetService {
	
	private static final String ERROR_MESSAGE = "Pet is not found";
	
	@Autowired
	private PetRepository repo;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<PetView> findAll(
			Optional<Integer> shelterId, 
			Optional<Float> minAge, 
			Optional<Float> maxAge, 
			Optional<Gender> gender, 
			Optional<Specie> specie,
			Optional<String> labelIds) {
		
		List<Pet> list = findAllPets(shelterId, minAge, maxAge, gender, specie, labelIds);
		
		return list.stream()
				.map(PetView::of)
				.toList();
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
	
	@SuppressWarnings("unchecked")
	private List<Pet> findAllPets(
			Optional<Integer> shelterId,
			Optional<Float> minAge, 
			Optional<Float> maxAge,
			Optional<Gender> gender,
			Optional<Specie> specie,
			Optional<String> labelIds) {
		
		StringBuilder builder = new StringBuilder("SELECT pet.* FROM pet ");
		
		var whereClauses = new ArrayList<String>();
		whereClauses.add("pet.deleted = false");
		
		if(!labelIds.isEmpty()) {
			var ids = labelIds.get();		
			whereClauses.add("pl.label_id in (" + ids + ")");

			builder.append("INNER JOIN pet_label pl ON pl.pet_id = pet.id ");	
		}
		
		shelterId.ifPresent(id -> whereClauses.add("pet.shelter_id = " + id));
		
		minAge.ifPresent(min -> whereClauses.add("pet.age >= " + min));
		maxAge.ifPresent(max -> whereClauses.add("pet.age <= " + max));

		specie.ifPresent(value -> whereClauses.add("pet.specie = " + value.name()));
		gender.ifPresent(value -> whereClauses.add("pet.gender = " + value.name()));
		
		builder.append("WHERE ");
		builder.append(whereClauses.stream()
				.collect(Collectors.joining(" AND ")));
		
		System.out.println(builder.toString());
		
		return entityManager
				.createNativeQuery(builder.toString(), Pet.class)
				.getResultList();
		
	}

}
