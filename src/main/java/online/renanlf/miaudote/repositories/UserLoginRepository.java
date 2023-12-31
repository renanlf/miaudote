package online.renanlf.miaudote.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import online.renanlf.miaudote.model.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
	
	@Query(value = "SELECT u FROM UserLogin u WHERE u.token = :token")
	Optional<UserLogin> findByToken(@Param("token") String token);

	@Query(value = "SELECT u FROM UserLogin u WHERE u.email = :email AND u.password = :password")
	Optional<UserLogin> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
	
	@Query(value = "SELECT u FROM UserLogin u WHERE u.email = :email")
	Optional<UserLogin> findByEmail(@Param("email") String email);
}
