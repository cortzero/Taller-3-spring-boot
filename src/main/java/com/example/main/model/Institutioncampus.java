package com.example.main.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the INSTITUTIONCAMPUS database table.
 * 
 */
@Entity
@NamedQuery(name="Institutioncampus.findAll", query="SELECT i FROM Institutioncampus i")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Institutioncampus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSTITUTIONCAMPUS_INSTCAMID_GENERATOR", sequenceName="INSTITUTIONCAMPUS_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTITUTIONCAMPUS_INSTCAMID_GENERATOR")
	@Column(name="INSTCAM_ID")
	private long instcamId;

	@Column(name="INSTCAM_NAME")
	@NotBlank(message = "Campus name must not be empty", groups = FirstGroup.class)
	private String instcamName;

	@Column(name="INSTCAM_OCCUPATION")
	@DecimalMin(value = "0.0", message = "Occupation must be 0", groups = FirstGroup.class)	//Creación
	@DecimalMax(value = "0.0", message = "Occupation must be 0", groups = FirstGroup.class)
	@DecimalMin(value = "0.0", message = "Occupation must be 0", groups = SecondGroup.class) //Edición
	@DecimalMax(value = "0.0", message = "Occupation must be 0", groups = SecondGroup.class)
	private BigDecimal instcamOccupation;

	//bi-directional many-to-one association to Device
	@OneToMany(mappedBy="institutioncampus")
	private List<Device> devices;

	//bi-directional many-to-one association to HatCapacitydetail
	@OneToMany(mappedBy="institutioncampus")
	@JsonIgnore
	private List<HatCapacitydetail> hatCapacitydetails;

	//bi-directional many-to-one association to Institution
	@ManyToOne
	@JoinColumn(name="INST_INST_ID")
	@NotNull(message = "A campus must be asociated to an institute.", groups = SecondGroup.class)
	private Institution institution;

	//bi-directional many-to-one association to Physicalspace
	@OneToMany(mappedBy="institutioncampus")
	private List<Physicalspace> physicalspaces;

	//bi-directional many-to-one association to Visit
	@OneToMany(mappedBy="institutioncampus")
	private List<Visit> visits;

	//bi-directional many-to-one association to Visitreason
	@OneToMany(mappedBy="institutioncampus")
	private List<Visitreason> visitreasons;

	public Institutioncampus() {
	}

	public long getInstcamId() {
		return this.instcamId;
	}

	public void setInstcamId(long instcamId) {
		this.instcamId = instcamId;
	}

	public String getInstcamName() {
		return this.instcamName;
	}

	public void setInstcamName(String instcamName) {
		this.instcamName = instcamName;
	}

	public BigDecimal getInstcamOccupation() {
		return this.instcamOccupation;
	}

	public void setInstcamOccupation(BigDecimal instcamOccupation) {
		this.instcamOccupation = instcamOccupation;
	}

	public List<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Device addDevice(Device device) {
		getDevices().add(device);
		device.setInstitutioncampus(this);

		return device;
	}

	public Device removeDevice(Device device) {
		getDevices().remove(device);
		device.setInstitutioncampus(null);

		return device;
	}

	public List<HatCapacitydetail> getHatCapacitydetails() {
		return this.hatCapacitydetails;
	}

	public void setHatCapacitydetails(List<HatCapacitydetail> hatCapacitydetails) {
		this.hatCapacitydetails = hatCapacitydetails;
	}

	public HatCapacitydetail addHatCapacitydetail(HatCapacitydetail hatCapacitydetail) {
		getHatCapacitydetails().add(hatCapacitydetail);
		hatCapacitydetail.setInstitutioncampus(this);

		return hatCapacitydetail;
	}

	public HatCapacitydetail removeHatCapacitydetail(HatCapacitydetail hatCapacitydetail) {
		getHatCapacitydetails().remove(hatCapacitydetail);
		hatCapacitydetail.setInstitutioncampus(null);

		return hatCapacitydetail;
	}

	public Institution getInstitution() {
		return this.institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<Physicalspace> getPhysicalspaces() {
		return this.physicalspaces;
	}

	public void setPhysicalspaces(List<Physicalspace> physicalspaces) {
		this.physicalspaces = physicalspaces;
	}

	public Physicalspace addPhysicalspace(Physicalspace physicalspace) {
		getPhysicalspaces().add(physicalspace);
		physicalspace.setInstitutioncampus(this);

		return physicalspace;
	}

	public Physicalspace removePhysicalspace(Physicalspace physicalspace) {
		getPhysicalspaces().remove(physicalspace);
		physicalspace.setInstitutioncampus(null);

		return physicalspace;
	}

	public List<Visit> getVisits() {
		return this.visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	public Visit addVisit(Visit visit) {
		getVisits().add(visit);
		visit.setInstitutioncampus(this);

		return visit;
	}

	public Visit removeVisit(Visit visit) {
		getVisits().remove(visit);
		visit.setInstitutioncampus(null);

		return visit;
	}

	public List<Visitreason> getVisitreasons() {
		return this.visitreasons;
	}

	public void setVisitreasons(List<Visitreason> visitreasons) {
		this.visitreasons = visitreasons;
	}

	public Visitreason addVisitreason(Visitreason visitreason) {
		getVisitreasons().add(visitreason);
		visitreason.setInstitutioncampus(this);

		return visitreason;
	}

	public Visitreason removeVisitreason(Visitreason visitreason) {
		getVisitreasons().remove(visitreason);
		visitreason.setInstitutioncampus(null);

		return visitreason;
	}

}