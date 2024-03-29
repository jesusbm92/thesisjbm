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
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
	private Exercise exercise;
	private Statistic statistic;
	private Collection<Metadata> metadata;
	private Collection<Answer> answers;
	private User owner;

	public Question() {
		super();
		metadata = new ArrayList<Metadata>();
		answers = new ArrayList<Answer>();

	}
	
	@Valid
	@ManyToOne(optional = true)
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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
	@Column(length = 50000)
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
	@ManyToOne(optional = false)
	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercises) {
		this.exercise = exercises;
	}

	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
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
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	@Fetch(FetchMode.SELECT)
	public Collection<Metadata> getMetadata() {
		return metadata;
	}

	public void setMetadata(Collection<Metadata> metadata) {
		this.metadata = metadata;
	}

	@Valid
	@NotNull
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "question" ,cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}

}
