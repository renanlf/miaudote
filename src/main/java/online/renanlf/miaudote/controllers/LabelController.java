package online.renanlf.miaudote.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import online.renanlf.miaudote.model.Label;
import online.renanlf.miaudote.services.LabelService;

/**
 * this class implements a controller for labels. 
 * It is strongly inspired by Spring tutorial https://spring.io/guides/tutorials/rest/
 * @author renanlf
 *
 */
@RestController
@RequestMapping("/labels")
public class LabelController extends ErrorHandler{	
	@Autowired
	private LabelService service;
	
	@GetMapping
	public List<Label> getLabels(
			@RequestParam(name = "name", defaultValue = "") String name) {
		
		if(name.isBlank()) return service.findAll();
		
		return service.findAllByName(name);
	}
	
	@PostMapping
	public Label postLabel(Authentication authentication, @Valid @RequestBody Label label) {
		return service.save(label);
	}
	
	@GetMapping("/{id}")
	public Label getLabel(@PathVariable long id) {
		return service.findById(id);
	}
	
	@PutMapping("/{id}")
	public Label putLabel(@Valid @RequestBody Label newLabel, @PathVariable long id) {	
		try {
			return service.update(id, newLabel);
		} catch (EntityNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteLabel(@PathVariable long id) {
		service.delete(id);
	}
	
}
