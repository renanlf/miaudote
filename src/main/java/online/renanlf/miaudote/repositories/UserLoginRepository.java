package online.renanlf.miaudote.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import online.renanlf.miaudote.model.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM login u WHERE u.token = :token AND deleted = false")
	Optional<UserLogin> findByToken(@Param("token") String token);

	@Query(nativeQuery = true,
			value = "SELECT * FROM login u WHERE u.email = :email AND u.password = :password AND deleted = false")
	Optional<UserLogin> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM login u WHERE u.email = :email AND deleted = false")
	Optional<UserLogin> findByEmail(@Param("email") String email);
}
