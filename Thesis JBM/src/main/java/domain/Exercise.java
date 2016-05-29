package domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private Exam exam;
	private Collection<Question> questions;
	
	public Exercise() {
		super();
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
	@Column(length = 50000)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "exercise",cascade=CascadeType.ALL)
	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}
	
	
	
	
}
