package domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Question extends DomainEntity {

	private String name;
	private String text;
	private String xml;
	private String difficulty;
	private Double weight;
	private Double weightfail;

	// Relationship
	private Collection<Exercise> exercises;
	private Statistic statistic;
	private Collection<Metadata> metadata;
	private Collection<Answer> answers;

	public Question() {
		super();
		exercises = new ArrayList<Exercise>();
		metadata = new ArrayList<Metadata>();
		answers = new ArrayList<Answer>();

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
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	@Valid
	@NotNull
	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@Valid
	@NotNull
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "questions")
	public Collection<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Collection<Exercise> exercises) {
		this.exercises = exercises;
	}

	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	@Valid
	public Double getWeightfail() {
		return weightfail;
	}

	public void setWeightfail(Double weightfail) {
		this.weightfail = weightfail;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Metadata> getMetadata() {
		return metadata;
	}

	public void setMetadata(Collection<Metadata> metadata) {
		this.metadata = metadata;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "question" ,cascade=CascadeType.PERSIST)
	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}

}
