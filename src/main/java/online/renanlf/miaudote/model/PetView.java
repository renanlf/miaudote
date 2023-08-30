package online.renanlf.miaudote.model;

import java.util.List;

import lombok.Data;

public @Data class PetView {
	
	public PetView(Pet pet) {
		this.id = pet.getId();
		this.shelter = pet.getShelter();
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
	
	public static PetView of(Pet pet) {
		return new PetView(pet);
	}
	
	private long id;
	
	private PetShelter shelter;
	
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
