package online.renanlf.miaudote.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Table(name = "login")
@EqualsAndHashCode(callSuper = false)
public @Data class UserLogin implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6671139337337499120L;

	@Id @GeneratedValue
	private long id;
	
	@Column(name = "email", unique = true)
	@NotBlank(message = "Email must be provided")
	private String email;

	@NotBlank(message = "Name must be provided")
	private String name;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "Password must be not null")
	private String password;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(unique = true)
	private String token;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(role.name()));
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public String getUsername() {
		return email;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
}
