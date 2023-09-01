package online.renanlf.miaudote.model;

import java.util.List;

import lombok.Data;

public @Data class PetDto {
	
	public PetDto(Pet pet) {
		this.id = pet.getId();
		this.shelterId = pet.getShelter().getId();
		this.shelterName = pet.getShelter().getName();
		this.specie = pet.getSpecie();
		this.labels = pet.getLabels();
		this.likes = pet.getLikers().size();
		this.adoptionAttempts = pet.getAdopts().size();
		this.imagesUrls = pet.getImagesUrls();
		this.name = pet.getName();
		this.bio = pet.getBio();
		this.age = pet.getAge();
		this.gender = pet.getGender();
	}
	
	public static PetDto of(Pet pet) {
		return new PetDto(pet);
	}
	
	private long id;
	
	private long shelterId;
	
	private String shelterName;
	
	private Specie specie;
	
	private List<Label> labels;
	
	private int likes;
	
	private int adoptionAttempts;
	
	private List<String> imagesUrls;
	
	private String name;	
	
	private Gender gender;	
	private float age;	
	private String bio;

	
}
