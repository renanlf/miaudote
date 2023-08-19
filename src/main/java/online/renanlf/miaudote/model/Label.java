package online.renanlf.miaudote.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public @Data class Label {
	@Id @GeneratedValue
	private long id;
	private String tagName;
	private boolean active;
}
