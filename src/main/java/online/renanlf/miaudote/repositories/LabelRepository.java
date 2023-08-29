package online.renanlf.miaudote.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import online.renanlf.miaudote.model.Label;

public interface LabelRepository extends ListCrudRepository<Label, Long> {

	@Query(nativeQuery = true,
			value = "SELECT * FROM label l WHERE l.tag_name like %?1% and l.deleted = false")
	List<Label> findAllByName(String name);

}
