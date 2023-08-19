package online.renanlf.miaudote.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import online.renanlf.miaudote.model.Label;
import online.renanlf.miaudote.repositories.LabelRepository;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(LabelController.class)
public class LabelControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private LabelRepository repo;
	
	@Test
	public void test() throws Exception {
		Label label = new Label();
		label.setTagName("testLabel");
		
		when(repo.findAll()).thenReturn(Collections.singletonList(label));
		
		mvc.perform(get("/labels")
				.contentType(MediaType.APPLICATION_JSON))
	      		.andExpect(status().isOk())
	      		.andExpect(jsonPath("$", hasSize(1)))
	      		.andExpect(jsonPath("$[0].tagName", is("testLabel")));
	}
}
