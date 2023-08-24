package online.renanlf.miaudote.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
// following soft-delete example from https://www.baeldung.com/spring-jpa-soft-delete
@Table(name = "label")
@SQLDelete(sql = "UPDATE label SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public @Data class Label {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank(message = "The tag name must be not empty")
	private String tagName;
	
	// to hide this property from JSON mapped object
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean deleted = false;
}
