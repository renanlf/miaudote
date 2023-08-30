package online.renanlf.miaudote.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import online.renanlf.miaudote.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

//	@Query(nativeQuery = true,
//			value = "SELECT * FROM pet p WHERE :where")
//	List<Pet> findAll(@Param(value = "where") String where);
//	
//	@Query(nativeQuery = true,
//			value = "SELECT * FROM pet p "
//					+ "INNER JOIN pet_label pl ON pl.pet_id = p.id "
//					+ "WHERE :where AND pl.label_id in (:labels)")
//	List<Pet> findAll(@Param(value = "where") String where, 
//			@Param(value = "labels") String labels);
}
