package com.gb.statistics.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="personpagerank")
@NamedQuery(name="Personpagerank.findAll", query="SELECT p FROM Personpagerank p")
public class Personpagerank implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private PersonpagerankPK id;

	@Column(nullable=false)
	private int rank;

	@ManyToOne
	@JoinColumn(name="pageid", nullable=false, insertable=false, updatable=false)
	private Page page;

	@ManyToOne
	@JoinColumn(name="personid", nullable=false, insertable=false, updatable=false)
	private Person person;

	public Personpagerank() {
	}

	public PersonpagerankPK getId() {
		return this.id;
	}

	public void setId(PersonpagerankPK id) {
		this.id = id;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}