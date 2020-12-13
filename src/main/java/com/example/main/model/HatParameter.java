package com.example.main.model;



import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;


/**
 * The persistent class for the HAT_PARAMETER database table.
 * 
 */
@Entity
@Table(name="HAT_PARAMETER")
@NamedQuery(name="HatParameter.findAll", query="SELECT h FROM HatParameter h")
public class HatParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HAT_PARAMETER_PARAMID_GENERATOR", sequenceName="HAT_PARAMETER_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HAT_PARAMETER_PARAMID_GENERATOR")
	@Column(name="PARAM_ID")
	private long paramId;

	@Column(name="PARAM_NAME")
	@NotBlank(groups = FirstGroup.class)
	private String paramName;

	@Column(name="PARAM_TYPE")
	@NotBlank(groups = FirstGroup.class)
	private String paramType;

	@Column(name="PARAM_VALUE")
	@NotBlank(groups = FirstGroup.class)
	private String paramValue;

	//bi-directional many-to-one association to Institution
	@ManyToOne
	@JoinColumn(name="INST_INST_ID")
	@NotNull(groups = SecondGroup.class)
	private Institution institution;

	public HatParameter() {
	}

	public long getParamId() {
		return this.paramId;
	}

	public void setParamId(long paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Institution getInstitution() {
		return this.institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

}