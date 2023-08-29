package online.renanlf.miaudote.model;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
//@DiscriminatorValue("1")
public @Data class PetShelter extends UserLogin {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6028936103609984139L;
	
	@NotBlank(message = "The bio must be not empty")
	private String bio;
	
	@NotBlank(message = "Provide a phone number")
	@Positive(message = "Provide just numbers")
	private String phoneNumber;
	
	@OneToMany(mappedBy = "shelter")
	private List<Pet> pets;
	
}
