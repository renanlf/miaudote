package online.renanlf.miaudote.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import online.renanlf.miaudote.model.Label;
import online.renanlf.miaudote.repositories.LabelRepository;

/**
 * this class implements a controller for labels. 
 * It is strongly inspired by Spring tutorial https://spring.io/guides/tutorials/rest/
 * @author renanlf
 *
 */
@RestController
@RequestMapping("/labels")
public class LabelController {	
	@Autowired
	private LabelRepository repo;
	
	private static final String ERROR_MESSAGE = "Label is not found";
	
	@GetMapping
	public List<Label> getLabels(
			@RequestParam(name = "name", defaultValue = "") String name) {
		
		if(name.isBlank()) return repo.findAll();
		
		return repo.findAllContainingName(name);
	}
	
	@PostMapping
	public Label postLabel(Authentication authentication, @Valid @RequestBody Label label) {
		System.out.println("Auth: " + authentication);
		return repo.save(label);
	}
	
	@GetMapping("/{id}")
	public Label getLabel(@PathVariable long id) {
		return repo.findById(id)
				.orElseThrow(() -> 
					new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_MESSAGE));
	}
	
	@PutMapping("/{id}")
	public Label putLabel(@Valid @RequestBody Label newLabel, @PathVariable long id) {	
		return repo.findById(id)
			.map(label -> {
				label.setTagName(newLabel.getTagName());
				return repo.save(label);
			})
			.orElseThrow(() -> 
				new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_MESSAGE));
	}
	
	@DeleteMapping("/{id}")
	public void deleteLabel(@PathVariable long id) {
		repo.findById(id)
			.ifPresent(label -> {
				repo.delete(label);
			});
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
