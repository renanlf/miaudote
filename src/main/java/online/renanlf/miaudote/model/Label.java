package online.renanlf.miaudote.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
public @Data class Label {
	@Id @GeneratedValue
	private long id;
	private String tagName;
}
