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
import online.renanlf.miaudote.model.Role;
import online.renanlf.miaudote.services.PetShelterService;
import online.renanlf.miaudote.services.UserLoginService;

@RestController
@RequestMapping("/petshelters")
public class PetShelterController extends ErrorHandler {

	@Autowired
	private PetShelterService shelterService;
	
	@Autowired
	private UserLoginService userService;
	
	@GetMapping
	public List<PetShelter> get() {
		return shelterService.findAll();
	}
	
	@PostMapping
	public PetShelter post(@Valid @RequestBody PetShelter shelter) {
		return shelterService.save(shelter);
	}
	
	@GetMapping("/{id}")
	public PetShelter getById(@PathVariable Long id) {
		return shelterService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		assertAuthorization(id);
		shelterService.delete(id);
	}
	
	@PutMapping("/{id}")
	public PetShelter put(@PathVariable Long id, @RequestBody PetShelter newOne) {
		assertAuthorization(id);
		return shelterService.update(id, newOne);
	}
	
	private void assertAuthorization(long id) {
		var authToken = SecurityContextHolder.getContext().getAuthentication();
		var user = userService.findByEmail(authToken.getPrincipal().toString());
		
		if(!(user.getRole() == Role.ADMIN 
				|| (user.getRole() == Role.SHELTER 
					&& user.getId() == id))) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}
}
