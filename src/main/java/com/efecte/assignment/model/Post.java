package com.efecte.assignment.model;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Setter;
import lombok.AccessLevel;
import lombok.Getter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name="post")
public class Post extends Auditable {
	
	@Id
    @GeneratedValue(generator = "post_generator")
    @SequenceGenerator(
            name = "post_generator",
            sequenceName = "post_sequence",
            initialValue = 50
    )
	private Long id;
	
	@Column(columnDefinition = "text")
	@Size(min=0, max=200)
	private String message;
	
	@Column(columnDefinition = "text")
	private String color = "e1b500";
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
