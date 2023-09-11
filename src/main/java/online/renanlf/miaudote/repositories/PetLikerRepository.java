package online.renanlf.miaudote.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import online.renanlf.miaudote.model.PetLiker;

public interface PetLikerRepository extends JpaRepository<PetLiker, Long> {

	@Query("SELECT l FROM PetLiker l WHERE l.email = :email AND l.deleted = false")
	Optional<PetLiker> findByEmail(@Param("email") String email);
}
