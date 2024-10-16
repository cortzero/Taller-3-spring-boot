package com.example.main.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * The persistent class for the USERR database table.
 * 
 */
@Entity
@NamedQuery(name="Userr.findAll", query="SELECT u FROM Userr u")
public class Userr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERR_USERID_GENERATOR", sequenceName="USERR_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERR_USERID_GENERATOR")
	@Column(name="USER_ID")
	private long userId;

	@NotNull
	@Column(name="INST_INST_ID")	
	private BigDecimal instInstId;
	
	@NotBlank
	@Size(min = 3)
	@Column(name="USER_NAME")	
	private String userName;

	@NotBlank
	@Size(min = 3)
	@Column(name="USER_PASSWORD")
	private String userPassword;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@NotNull
	@JoinColumn(name="PERS_PERS_ID")	
	private Person person;

	public Userr() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public BigDecimal getInstInstId() {
		return this.instInstId;
	}

	public void setInstInstId(BigDecimal instInstId) {
		this.instInstId = instInstId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}