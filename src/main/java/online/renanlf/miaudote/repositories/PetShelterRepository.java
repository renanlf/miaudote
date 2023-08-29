package online.renanlf.miaudote.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import online.renanlf.miaudote.model.PetShelter;

public interface PetShelterRepository extends ListCrudRepository<PetShelter, Long> {

	@Query("SELECT s FROM PetShelter s WHERE s.email = :email AND deleted = false")
	Optional<PetShelter> findByEmail(@Param("email") String email);
}
