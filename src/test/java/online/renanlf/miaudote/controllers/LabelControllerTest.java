package online.renanlf.miaudote.controllers;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import online.renanlf.miaudote.model.Label;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LabelControllerTest {
	
	@Test
	@Order(1)
	public void testGetEmpty(@Autowired WebTestClient webClient) throws Exception {
		webClient
			.get().uri("/labels")
			.exchange()
			.expectStatus().isOk()
			.expectBody(List.class).isEqualTo(Collections.emptyList());
	}
	
	@Test
	@Order(2)
	public void testPost(@Autowired WebTestClient webClient) throws Exception {
		var label = new Label();
		label.setTagName("teste");
		
		webClient
			.post().uri("/labels")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(label)
			.exchange()
		    .expectStatus().isOk()
		    .expectBody().jsonPath("tagName").isEqualTo("teste");
		
		webClient
			.get().uri("/labels")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("[{\"id\":1,\"tagName\":\"teste\"}]");
	}
	
	@Test
	@Order(3)
	public void testPut(@Autowired WebTestClient webClient) throws Exception {
		var label = new Label();
		label.setTagName("testeDiferente");
		label.setId(1);
		
		webClient
			.put().uri("/labels/15")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(label)
			.exchange()
		    .expectStatus().isNotFound();
		
		webClient
			.put().uri("/labels/1")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(label)
			.exchange()
		    .expectStatus().isOk();
		
		webClient
			.get().uri("/labels")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("[{\"id\":1,\"tagName\":\"testeDiferente\"}]");
	}
	
	@Test
	@Order(4)
	public void testGetById(@Autowired WebTestClient webClient) throws Exception {
		webClient
			.get().uri("/labels/1")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("{\"id\":1,\"tagName\":\"testeDiferente\"}");
		
		webClient
			.get().uri("/labels/2")
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Test
	@Order(5)
	public void testDelete(@Autowired WebTestClient webClient) throws Exception {
		webClient
			.delete().uri("/labels/1")
			.exchange()
			.expectStatus().isOk();
		
		webClient
			.get().uri("/labels/1")
			.exchange()
			.expectStatus().isNotFound();
	}
}
