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
import online.renanlf.miaudote.model.PetLiker;
import online.renanlf.miaudote.model.Role;
import online.renanlf.miaudote.services.PetLikerService;
import online.renanlf.miaudote.services.UserLoginService;

@RestController
@RequestMapping("/petlikers")
public class PetLikerController {
	
	@Autowired
	private PetLikerService likerService;
	
	@Autowired
	private UserLoginService userService;
	
	@GetMapping
	public List<PetLiker> get() {
		return likerService.findAll();
	}
	
	@PostMapping
	public PetLiker post(@Valid @RequestBody PetLiker shelter) {
		return likerService.save(shelter);
	}
	
	@GetMapping("/{id}")
	public PetLiker getById(@PathVariable Long id) {
		return likerService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		assertAuthorization(id);
		likerService.delete(id);
	}
	
	@PutMapping("/{id}")
	public PetLiker put(@PathVariable Long id, @RequestBody PetLiker newOne) {
		assertAuthorization(id);
		return likerService.update(id, newOne);
	}
	
	private void assertAuthorization(long id) {
		var authToken = SecurityContextHolder.getContext().getAuthentication();
		var user = userService.findByEmail(authToken.getPrincipal().toString());
		
		if(!(user.getRole() == Role.ADMIN 
				|| (user.getRole() == Role.LIKER 
					&& user.getId() == id))) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}
}