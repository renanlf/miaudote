package online.renanlf.miaudote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import online.renanlf.miaudote.model.LoginRequisition;
import online.renanlf.miaudote.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public String performLogin(@Valid @RequestBody LoginRequisition loginReq) {		
		return loginService.authenticate(loginReq);
	}
}
