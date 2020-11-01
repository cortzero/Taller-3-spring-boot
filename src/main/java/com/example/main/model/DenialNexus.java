package com.example.main.model;



import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the DENIAL_NEXUS database table.
 *
 */
@Entity
@Table(name = "DENIAL_NEXUS")
@NamedQuery(name = "DenialNexus.findAll", query = "SELECT d FROM DenialNexus d")
public class DenialNexus implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DenialNexusPK id;

	private String dumy;

	// bi-directional many-to-one association to Accessdenialevent
	@ManyToOne
	@JoinColumn(name = "ACCDENEVE_ACCDENEVE_ID", insertable = false, updatable = false)
	private Accessdenialevent accessdenialevent;

	// bi-directional many-to-one association to Epidemnexus
	@ManyToOne
	@JoinColumn(name = "EPINEX_EPINEX_ID", insertable = false, updatable = false)
	private Epidemnexus epidemnexus;

	public DenialNexus() {
	}

	public Accessdenialevent getAccessdenialevent() {
		return this.accessdenialevent;
	}

	public String getDumy() {
		return this.dumy;
	}

	public Epidemnexus getEpidemnexus() {
		return this.epidemnexus;
	}

	public DenialNexusPK getId() {
		return this.id;
	}

	public void setAccessdenialevent(Accessdenialevent accessdenialevent) {
		this.accessdenialevent = accessdenialevent;
	}

	public void setDumy(String dumy) {
		this.dumy = dumy;
	}

	public void setEpidemnexus(Epidemnexus epidemnexus) {
		this.epidemnexus = epidemnexus;
	}

	public void setId(DenialNexusPK id) {
		this.id = id;
	}

}