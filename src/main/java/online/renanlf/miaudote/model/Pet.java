package online.renanlf.miaudote.model;

import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "pet")
@SQLDelete(sql = "UPDATE pet SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class, 
		property = "id")
public @Data class Pet {
	
	@Id @GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "shelter_id")
	private PetShelter shelter;
	
	@NotNull(message = "Provide a specie for this pet")
	@Enumerated(EnumType.STRING)
	private Specie specie;
	
	@NotNull(message = "Provide a gender for this pet")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private List<String> imagesUrls = Collections.emptyList();
	
	@NotBlank(message = "Provide a name for this pet")
	private String name;
	
	private float age = -1;
	
	@NotBlank(message = "Provide a bio for this pet")
	private String bio;

	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean deleted = false;
	
	@ManyToMany
	@JoinTable(
			  name = "pet_label", 
			  joinColumns = @JoinColumn(name = "pet_id"), 
			  inverseJoinColumns = @JoinColumn(name = "label_id"))
	private List<Label> labels = Collections.emptyList();
	
	@ManyToMany
	@JoinTable(
			  name = "pet_likes", 
			  joinColumns = @JoinColumn(name = "pet_id"), 
			  inverseJoinColumns = @JoinColumn(name = "liker_id"))
	private List<PetLiker> likers = Collections.emptyList();
	
	@ManyToMany
	@JoinTable(
			  name = "pet_adoptions", 
			  joinColumns = @JoinColumn(name = "pet_id"), 
			  inverseJoinColumns = @JoinColumn(name = "adopter_id"))
	private List<PetLiker> adopts = Collections.emptyList();

}
