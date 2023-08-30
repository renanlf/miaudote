package online.renanlf.miaudote.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public @Data class PetLiker {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToMany(mappedBy = "likers")
	private List<Pet> likes;

	@ManyToMany(mappedBy = "adopts")
	private List<Pet> adopts;

	private boolean hasPreviousPets;
	private Date birthDate;
	private String bio;
	private String avatarUrl;
	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return Collections.singletonList(new SimpleGrantedAuthority("LIKER"));
//	}
}
