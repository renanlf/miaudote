package online.renanlf.miaudote.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DiscriminatorValue(value = "2")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class, 
		property = "id")
public @Data class PetLiker extends UserLogin {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7368829338017703498L;

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToMany(mappedBy = "likers")
	private List<Pet> likes;

	@ManyToMany(mappedBy = "adopts")
	private List<Pet> adopts;

	private boolean hasPreviousPets = false;
	@NotNull(message = "Must provide the birth date")
	private Date birthDate;
	
	@NotBlank(message = "The bio must be not empty")
	private String bio;
	private String avatarImage;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean deleted = false;
}
