package com.example.main.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;

import java.util.List;

/**
 * The persistent class for the PHYSICALSPACETYPE database table.
 * 
 */
@Entity
@NamedQuery(name="Physicalspacetype.findAll", query="SELECT p FROM Physicalspacetype p")
public class Physicalspacetype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PHYSICALSPACETYPE_PHYSPCTYPEID_GENERATOR", sequenceName="PHYSICALSPACETYPE_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PHYSICALSPACETYPE_PHYSPCTYPEID_GENERATOR")
	@Column(name="PHYSPCTYPE_ID")
	private long physpctypeId;

	@Column(name="PHYSPCTYPE_EXTID")
	@NotBlank(message = "External id must not be empty", groups = FirstGroup.class)
	@Pattern(regexp = "(?<!\\d)\\d{5}(?!\\d)", message = "External ID must have exactly 5 digits", groups = FirstGroup.class)
	private String physpctypeExtid;

	@Column(name="PHYSPCTYPE_IMPLIESCOMM")
	@NotBlank(message = "Community must not be empty", groups = FirstGroup.class)
	private String physpctypeImpliescomm;

	@Column(name="PHYSPCTYPE_NAME")
	@NotBlank(message = "Physical space type name must not be empty", groups = FirstGroup.class)
	private String physpctypeName;

	//bi-directional many-to-one association to Physicalspace
	@OneToMany(mappedBy="physicalspacetype")
	private List<Physicalspace> physicalspaces;

	//bi-directional many-to-one association to Institution
	@ManyToOne
	@JoinColumn(name="INST_INST_ID")
	@NotNull(message = "The physical space type must be asociated with an institution", groups = SecondGroup.class)
	private Institution institution;

	public Physicalspacetype() {
	}

	public long getPhyspctypeId() {
		return this.physpctypeId;
	}

	public void setPhyspctypeId(long physpctypeId) {
		this.physpctypeId = physpctypeId;
	}

	public String getPhyspctypeExtid() {
		return this.physpctypeExtid;
	}

	public void setPhyspctypeExtid(String physpctypeExtid) {
		this.physpctypeExtid = physpctypeExtid;
	}

	public String getPhyspctypeImpliescomm() {
		return this.physpctypeImpliescomm;
	}

	public void setPhyspctypeImpliescomm(String physpctypeImpliescomm) {
		this.physpctypeImpliescomm = physpctypeImpliescomm;
	}

	public String getPhyspctypeName() {
		return this.physpctypeName;
	}

	public void setPhyspctypeName(String physpctypeName) {
		this.physpctypeName = physpctypeName;
	}

	public List<Physicalspace> getPhysicalspaces() {
		return this.physicalspaces;
	}

	public void setPhysicalspaces(List<Physicalspace> physicalspaces) {
		this.physicalspaces = physicalspaces;
	}

	public Physicalspace addPhysicalspace(Physicalspace physicalspace) {
		getPhysicalspaces().add(physicalspace);
		physicalspace.setPhysicalspacetype(this);

		return physicalspace;
	}

	public Physicalspace removePhysicalspace(Physicalspace physicalspace) {
		getPhysicalspaces().remove(physicalspace);
		physicalspace.setPhysicalspacetype(null);

		return physicalspace;
	}

	public Institution getInstitution() {
		return this.institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

}