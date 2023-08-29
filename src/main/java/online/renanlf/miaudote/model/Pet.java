package online.renanlf.miaudote.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public @Data class Pet {
	
	@Id @GeneratedValue
	private long id;
	
	@ManyToOne
	private PetShelter shelter;
	
	private Specie specie;
	
	@ManyToMany
	@JoinTable(
			  name = "pet_label", 
			  joinColumns = @JoinColumn(name = "pet_id"), 
			  inverseJoinColumns = @JoinColumn(name = "label_id"))
	private List<Label> labels;
	
	@ManyToMany
	@JoinTable(
			  name = "pet_likes", 
			  joinColumns = @JoinColumn(name = "pet_id"), 
			  inverseJoinColumns = @JoinColumn(name = "liker_id"))
	private List<PetLiker> likers;
	
	@ManyToMany
	@JoinTable(
			  name = "pet_adoptions", 
			  joinColumns = @JoinColumn(name = "pet_id"), 
			  inverseJoinColumns = @JoinColumn(name = "adopter_id"))
	private List<PetLiker> adopts;
	
	private List<String> imagesUrls;
	
	private String name;	
	// true: female false: male
	private boolean gender;	
	private float age;	
	private String bio;
	private boolean active;

}
