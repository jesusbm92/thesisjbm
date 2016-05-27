package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.engine.FetchTiming;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Exam extends DomainEntity {

	private String name;
	private Date date;
	private String difficulty;
	private String xml;

	// Relationship
	private Collection<Exercise> exercises;
	private Collection<User> owners;

	public Exam() {
		super();
		exercises = new ArrayList<Exercise>();
		owners = new ArrayList<User>();

	}

	@Valid
	@ManyToMany(fetch = FetchType.EAGER)
	public Collection<User> getOwners() {
		return owners;
	}

	public void setOwners(Collection<User> owners) {
		this.owners = owners;
	}

	@Valid
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	@Column(length = 50000)
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	@Valid
	@NotNull
	@OneToMany (fetch = FetchType.EAGER, mappedBy= "exam", cascade=CascadeType.ALL)
	public Collection<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Collection<Exercise> exercises) {
		this.exercises = exercises;
	}

}
