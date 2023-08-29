package online.renanlf.miaudote.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import online.renanlf.miaudote.model.Label;
import online.renanlf.miaudote.repositories.LabelRepository;

@Service
public class LabelService {

	@Autowired
	private LabelRepository repo;
	
	private static final String ERROR_MESSAGE = "Label is not found";
	

	public List<Label> findAll() {
		return repo.findAll();
	}

	public List<Label> findAllByName(String name) {
		return repo.findAllByName(name);
	}

	public Label findById(long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
	}
	
	public Label save(Label label) {
		return repo.save(label);
	}
	
	public void delete(long id) {
		repo.findById(id)
		.ifPresent(label -> {
			repo.delete(label);
		});
	}
	
	public Label update(Long id, Label newLabel) {
		return repo.findById(id)
		.map(label -> {
			label.setTagName(newLabel.getTagName());
			return repo.save(label);
		})
		.orElseThrow(() -> 
			new EntityNotFoundException(ERROR_MESSAGE + ":" + id));

	}
}
