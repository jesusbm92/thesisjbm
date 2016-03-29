package domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class Exercise extends DomainEntity{

	//El peso del ejercicio se calcula usando la suma del peso de las preguntas que contiene
	private String name;
	private String text;
	
	//Relationship
	private Collection<Exam> exams;
	private Collection<Question> questions;
	
	public Exercise() {
		super();
		exams = new ArrayList<Exam>();
		questions = new ArrayList<Question>();
	}

	@Valid
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Valid
	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "exercises")
	public Collection<Exam> getExams() {
		return exams;
	}

	public void setExams(Collection<Exam> exams) {
		this.exams = exams;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}
	
	
	
	
}
