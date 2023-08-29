package online.renanlf.miaudote.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import online.renanlf.miaudote.model.PetShelter;
import online.renanlf.miaudote.services.PetShelterService;

@RestController
@RequestMapping("/petshelters")
public class PetShelterController extends ErrorHandler {

	@Autowired
	private PetShelterService service;
	
	@GetMapping
	public List<PetShelter> get() {
		return service.findAll();
	}
	
	@PostMapping
	public PetShelter post(@Valid @RequestBody PetShelter shelter) {
		return service.save(shelter);
	}
	
	@GetMapping("/{id}")
	public PetShelter getById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		var shelter = service.findByEmail(auth.getPrincipal().toString());
		
		if(shelter.getId() == id) service.delete(id);
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/{id}")
	public PetShelter put(@PathVariable Long id, @RequestBody PetShelter newOne) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		var shelter = service.findByEmail(auth.getPrincipal().toString());
		
		if(shelter.getId() == id) return service.update(id, newOne);
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}
}
