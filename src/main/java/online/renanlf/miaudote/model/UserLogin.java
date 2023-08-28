package online.renanlf.miaudote.model;

import java.util.Collection;
import java.util.Collections;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER)
//@DiscriminatorValue(value = "0")
@NoArgsConstructor
@Table(name = "login")
@EqualsAndHashCode(callSuper = false)
@SQLDelete(sql = "UPDATE login SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public @Data class UserLogin implements UserDetails {
	
	@Id @GeneratedValue
	private long id;
	
	@Column(name = "email", unique = true)
	private String email;

	private String name, password;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean deleted = false;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(unique = true)
	private String token;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
	}

	@Override
	public boolean isEnabled() {
		return !deleted;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
}
