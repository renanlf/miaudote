package online.renanlf.miaudote.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import online.renanlf.miaudote.model.PetShelter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetShelterE2ETest {
	
	@Test
	@Order(1)
	public void testEmptyGet(@Autowired WebTestClient webClient) {
		webClient
			.get()
			.uri("/petshelters")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("[]");
	}
	
	@Test
	@Order(2)
	public void testPost(@Autowired WebTestClient webClient) {
		var shelter = new PetShelter();
//		shelter.setName("Pet shelter1");
		shelter.setBio("This is a pretty pet shelter bio");
//		shelter.setEmail("meuemail@mail.com");
//		shelter.setPassword("123456");
		shelter.setPhoneNumber("123123123");
		
		webClient
			.post()
			.uri("/petshelters")
			.bodyValue(shelter)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(String.class).isEqualTo("[]");
	}
}
