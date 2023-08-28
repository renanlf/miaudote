package online.renanlf.miaudote;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import online.renanlf.miaudote.filters.JwtAuthenticationFilter;

/**
 * This class is heavely inspired by https://www.baeldung.com/spring-security-login
 * @author renanlf
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MySecurityConfig {
	 
	private final AuthenticationProvider authProvider;
	
	private final JwtAuthenticationFilter jwtAuthFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        		.csrf()
        		.disable()
                .authorizeHttpRequests()
                	.requestMatchers(HttpMethod.POST, "/login").permitAll()
                	
                	// ADMIN permissions
                	.requestMatchers(HttpMethod.POST, "/**").hasAuthority("ADMIN")
                	.requestMatchers(HttpMethod.GET, "/**").hasAuthority("ADMIN")
                	.requestMatchers(HttpMethod.PUT, "/**").hasAuthority("ADMIN")
                	.requestMatchers(HttpMethod.DELETE, "/**").hasAuthority("ADMIN")
                	
                	.anyRequest().authenticated()
                	
                	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                	
                	.and().authenticationProvider(authProvider)
                	
                	.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                
                .build();
	}
	
}
