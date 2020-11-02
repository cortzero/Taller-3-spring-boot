package com.example.main.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the PHYSICALSPACE database table.
 * 
 */
@Entity
@NamedQuery(name="Physicalspace.findAll", query="SELECT p FROM Physicalspace p")
public class Physicalspace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PHYSICALSPACE_PHYSPCID_GENERATOR", sequenceName="PHYSICALSPACE_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PHYSICALSPACE_PHYSPCID_GENERATOR")
	@Column(name="PHYSPC_ID")
	private long physpcId;

	@Column(name="PHYSPC_EXTID")
	@NotBlank(message = "External id must not be empty", groups = FirstGroup.class)
	@Pattern(regexp = "(?<!\\d)\\d{5}(?!\\d)", message = "External ID must have exactly 5 digits", groups = FirstGroup.class)
	private String physpcExtid;

	@Column(name="PHYSPC_NAME")
	@NotBlank(message = "Physical space name must not be empty", groups = FirstGroup.class)
	private String physpcName;

	@Column(name="PHYSPC_OCCUPATION")	
	private BigDecimal physpcOccupation;

	//bi-directional many-to-one association to Communityinstance
	@OneToMany(mappedBy="physicalspace")
	private List<Communityinstance> communityinstances;

	//bi-directional many-to-one association to HatCapacitydetail
	@OneToMany(mappedBy="physicalspace")
	private List<HatCapacitydetail> hatCapacitydetails;

	//bi-directional many-to-one association to Institutioncampus
	@ManyToOne
	@JoinColumn(name="INSTCAM_INSTCAM_ID")
	@NotNull(message = "The physical space must be asociated with an institution", groups = SecondGroup.class)
	private Institutioncampus institutioncampus;

	//bi-directional many-to-one association to Physicalspace
	@ManyToOne
	@JoinColumn(name="PHYSPC_PHYSPC_IDPARENT")	
	private Physicalspace physicalspace;

	//bi-directional many-to-one association to Physicalspace
	@OneToMany(mappedBy="physicalspace")
	private List<Physicalspace> physicalspaces;

	//bi-directional many-to-one association to Physicalspacetype
	@ManyToOne
	@JoinColumn(name="PHYSPCTYPE_PHYSPCTYPE_ID")
	@NotNull(message = "The physical space must be asociated with its type", groups = SecondGroup.class)
	private Physicalspacetype physicalspacetype;

	public Physicalspace() {
	}

	public long getPhyspcId() {
		return this.physpcId;
	}

	public void setPhyspcId(long physpcId) {
		this.physpcId = physpcId;
	}

	public String getPhyspcExtid() {
		return this.physpcExtid;
	}

	public void setPhyspcExtid(String physpcExtid) {
		this.physpcExtid = physpcExtid;
	}

	public String getPhyspcName() {
		return this.physpcName;
	}

	public void setPhyspcName(String physpcName) {
		this.physpcName = physpcName;
	}

	public BigDecimal getPhyspcOccupation() {
		return this.physpcOccupation;
	}

	public void setPhyspcOccupation(BigDecimal physpcOccupation) {
		this.physpcOccupation = physpcOccupation;
	}

	public List<Communityinstance> getCommunityinstances() {
		return this.communityinstances;
	}

	public void setCommunityinstances(List<Communityinstance> communityinstances) {
		this.communityinstances = communityinstances;
	}

	public Communityinstance addCommunityinstance(Communityinstance communityinstance) {
		getCommunityinstances().add(communityinstance);
		communityinstance.setPhysicalspace(this);

		return communityinstance;
	}

	public Communityinstance removeCommunityinstance(Communityinstance communityinstance) {
		getCommunityinstances().remove(communityinstance);
		communityinstance.setPhysicalspace(null);

		return communityinstance;
	}

	public List<HatCapacitydetail> getHatCapacitydetails() {
		return this.hatCapacitydetails;
	}

	public void setHatCapacitydetails(List<HatCapacitydetail> hatCapacitydetails) {
		this.hatCapacitydetails = hatCapacitydetails;
	}

	public HatCapacitydetail addHatCapacitydetail(HatCapacitydetail hatCapacitydetail) {
		getHatCapacitydetails().add(hatCapacitydetail);
		hatCapacitydetail.setPhysicalspace(this);

		return hatCapacitydetail;
	}

	public HatCapacitydetail removeHatCapacitydetail(HatCapacitydetail hatCapacitydetail) {
		getHatCapacitydetails().remove(hatCapacitydetail);
		hatCapacitydetail.setPhysicalspace(null);

		return hatCapacitydetail;
	}

	public Institutioncampus getInstitutioncampus() {
		return this.institutioncampus;
	}

	public void setInstitutioncampus(Institutioncampus institutioncampus) {
		this.institutioncampus = institutioncampus;
	}

	public Physicalspace getPhysicalspace() {
		return this.physicalspace;
	}

	public void setPhysicalspace(Physicalspace physicalspace) {
		this.physicalspace = physicalspace;
	}

	public List<Physicalspace> getPhysicalspaces() {
		return this.physicalspaces;
	}

	public void setPhysicalspaces(List<Physicalspace> physicalspaces) {
		this.physicalspaces = physicalspaces;
	}

	public Physicalspace addPhysicalspace(Physicalspace physicalspace) {
		getPhysicalspaces().add(physicalspace);
		physicalspace.setPhysicalspace(this);

		return physicalspace;
	}

	public Physicalspace removePhysicalspace(Physicalspace physicalspace) {
		getPhysicalspaces().remove(physicalspace);
		physicalspace.setPhysicalspace(null);

		return physicalspace;
	}

	public Physicalspacetype getPhysicalspacetype() {
		return this.physicalspacetype;
	}

	public void setPhysicalspacetype(Physicalspacetype physicalspacetype) {
		this.physicalspacetype = physicalspacetype;
	}

}