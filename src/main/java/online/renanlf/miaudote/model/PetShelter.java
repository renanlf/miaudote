package online.renanlf.miaudote.model;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "pet_shelter")
@SQLDelete(sql = "UPDATE pet_shelter SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class, 
		property = "id")
public @Data class PetShelter extends UserLogin {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6028936103609984139L;
	
	@NotBlank(message = "The bio must be not empty")
	private String bio;
	
	@OneToMany(mappedBy = "shelter")
	private List<Pet> pets;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean deleted = false;
	
}
