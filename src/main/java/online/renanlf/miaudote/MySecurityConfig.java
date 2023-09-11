package online.renanlf.miaudote;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
 
                	.requestMatchers(HttpMethod.GET, "/labels").permitAll()
                	
                	.requestMatchers(HttpMethod.POST, "/petshelters").permitAll()
                	.requestMatchers(HttpMethod.GET, "/petshelters").permitAll()
                	
                	.requestMatchers(HttpMethod.GET, "/pets").permitAll()
                	
                	.requestMatchers(HttpMethod.POST, "/petlikers").permitAll()
                	.requestMatchers(HttpMethod.GET, "/petlikers").permitAll()
                	
                	// SHELTER permissions
                	.requestMatchers("/petshelters/**").hasAuthority("SHELTER")
                	.requestMatchers(HttpMethod.POST, "/pets").hasAuthority("SHELTER")
                	.requestMatchers("/pets/**").hasAuthority("SHELTER")
                	
                	//LIKER permissions
                	.requestMatchers("/petlikers/**").hasAuthority("LIKER")
                	
                	// ADMIN permissions
                	.requestMatchers("/**").hasAuthority("ADMIN")
                	
                	.anyRequest().authenticated()
                	
                	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                	
                	.and().authenticationProvider(authProvider)
                	
                	.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                
                .build();
	}
	
}
