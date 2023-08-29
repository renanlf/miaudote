package online.renanlf.miaudote.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
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
import online.renanlf.miaudote.model.LoginRequisition;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LabelE2ETest {
	
	private static String token;
	
	@BeforeAll
	public static void createToken(@Autowired WebTestClient webClient) {
		Mono<LoginRequisition> mono = Mono.just(LoginRequisition.builder().email("leandrorenanf@gmail.com").password("admin").build());
				
		token = webClient.post().uri("/login")
			.contentType(MediaType.APPLICATION_JSON)
			.body(mono, LoginRequisition.class)
			.exchange()
			.expectBody(String.class)
			.returnResult().getResponseBody();
	}
	
	@Test
	@Order(1)
	public void testGetEmpty(@Autowired WebTestClient webClient) throws Exception {
		webClient
			.get().uri("/labels")
			.exchange()
			.expectStatus().isOk()
			.expectBody(List.class).isEqualTo(Collections.emptyList());
		
		webClient
			.get().uri("/labels")
			.header("Authorization", "Bearer " + token)
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
		    .expectStatus().isForbidden();
		
		webClient
			.post().uri("/labels")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(label)
			.exchange()
		    .expectStatus().isOk()
		    .expectBody().jsonPath("tagName").isEqualTo("teste");
		
		label.setTagName("    ");
		
		webClient
			.post().uri("/labels")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(label)
			.exchange()
		    .expectStatus().is4xxClientError()
		    .expectBody().jsonPath("tagName").isEqualTo("The tag name must be not empty");
		
		webClient
			.get().uri("/labels")
			.header("Authorization", "Bearer " + token)
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
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(label)
			.exchange()
		    .expectStatus().isForbidden();
		
		webClient
			.put().uri("/labels/1")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(label)
			.exchange()
		    .expectStatus().isOk();
		
		webClient
			.get().uri("/labels")
			.header("Authorization", "Bearer " + token)
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("[{\"id\":1,\"tagName\":\"testeDiferente\"}]");
	}
	
	@Test
	@Order(4)
	public void testGetById(@Autowired WebTestClient webClient) throws Exception {
		webClient
			.get().uri("/labels/1")
			.header("Authorization", "Bearer " + token)
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("{\"id\":1,\"tagName\":\"testeDiferente\"}");
		
		webClient
			.get().uri("/labels/2")
			.header("Authorization", "Bearer " + token)
			.exchange()
			.expectStatus().isForbidden();
	}
	
	@Test
	@Order(5)
	public void testDelete(@Autowired WebTestClient webClient) throws Exception {
		webClient
			.delete().uri("/labels/1")
			.header("Authorization", "Bearer " + token)
			.exchange()
			.expectStatus().isOk();
		
		webClient
			.get().uri("/labels/1")
			.header("Authorization", "Bearer " + token)
			.exchange()
			.expectStatus().isForbidden();
	}
	
	@Test
	@Order(6)
	public void testPostWithSomeId(@Autowired WebTestClient webClient) throws Exception {
		var label = new Label();
		label.setTagName("teste");
		// I am testing to see if the JPA will replace any id, regardless of its value.
		label.setId(30);
		
		webClient
			.post().uri("/labels")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(label)
			.exchange()
		    .expectStatus().isOk()
		    .expectBody().jsonPath("id").isEqualTo("2");
	}
	
	@Test
	@Order(7)
	public void testGetWithParam(@Autowired WebTestClient webClient) throws Exception {
		var names = Arrays.asList("carinhoso", "atencioso", "dorme na cama", "miador", "idoso", "carente");
		
		// posting some labels
		names.stream()
			.map(name -> {
				var label = new Label();
				label.setTagName(name);
				
				return label;
			})
			.forEach(label -> 
				webClient.post().uri("/labels")
					.header("Authorization", "Bearer " + token)
					.bodyValue(label).exchange()
					.expectStatus().isOk()
					.expectBody().jsonPath("tagName", label.getTagName())
			);
		
		// checking if it was added the labels
		webClient.get().uri("/labels")
			.header("Authorization", "Bearer " + token)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Label.class)
			.hasSize(names.size() + 1);
		
		var namesWithOso = names.stream()
				.filter(name -> 
					name.contains("oso")).collect(Collectors.toSet());
		
		var responseList = webClient.get().uri("/labels?name=oso")
			.header("Authorization", "Bearer " + token).exchange()
			.expectStatus().isOk()
			.expectBodyList(Label.class)
			.hasSize(namesWithOso.size())
			.returnResult()
			.getResponseBody();
		
		assertTrue(responseList
			.stream()
			.map(Label::getTagName)
			.collect(Collectors.toSet())
			.equals(namesWithOso));
		
		// delete some label and check if it updates the list
		Long someId = responseList.get(0).getId();
		responseList.remove(0);
		
		webClient.delete().uri("/labels/" + someId)
		.header("Authorization", "Bearer " + token)
			.exchange()
			.expectStatus().isOk();
		
		var newResponseList = webClient.get().uri("/labels?name=oso")
				.header("Authorization", "Bearer " + token)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Label.class)
				.hasSize(namesWithOso.size() - 1)
				.returnResult()
				.getResponseBody();
		
		assertTrue(newResponseList
			.stream()
			.collect(Collectors.toSet())
			.equals(responseList.stream().collect(Collectors.toSet())));
			
	}
}
