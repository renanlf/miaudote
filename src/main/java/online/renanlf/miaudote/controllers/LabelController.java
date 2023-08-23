package online.renanlf.miaudote.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import online.renanlf.miaudote.model.Label;
import online.renanlf.miaudote.repositories.LabelRepository;

/**
 * this class implements a controller for labels. 
 * It is strongly inspired by Spring tutorial https://spring.io/guides/tutorials/rest/
 * @author renanlf
 *
 */
@RestController
public class LabelController {
	
	@Autowired
	private LabelRepository repo;
	
	private static final String ERROR_MESSAGE = "Label is not found";
	
	@GetMapping("/labels")
	public List<Label> getLabels() {
		return repo.findAll();
	}
	
	@PostMapping("/labels")
	public Label postLabel(@RequestBody Label label) {
		return repo.save(label);
	}
	
	@GetMapping("/labels/{id}")
	public Label getLabel(@PathVariable long id) {
		return repo.findById(id)
				.orElseThrow(() -> 
					new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_MESSAGE));
	}
	
	@PutMapping("/labels/{id}")
	public Label putLabel(@RequestBody Label newLabel, @PathVariable long id) {		
		return repo.findById(id)
			.map(label -> {
				label.setTagName(newLabel.getTagName());
				return repo.save(label);
			})
			.orElseThrow(() -> 
				new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_MESSAGE));
	}
	
	@DeleteMapping("/labels/{id}")
	public void deleteLabel(@PathVariable long id) {
		repo.findById(id)
			.ifPresent(label -> {
				repo.delete(label);
			});
	}
}
