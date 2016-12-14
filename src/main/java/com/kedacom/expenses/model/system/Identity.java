package com.kedacom.expenses.model.system;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.kedacom.expenses.model.ExpBaseModel;

@XmlRootElement(name = "identity")
@XmlAccessorType(XmlAccessType.NONE)
public class Identity extends ExpBaseModel implements Serializable {

	protected Long id = Long.valueOf(0L);

	@XmlAttribute
	protected String name;

	@XmlAttribute
	protected String alias;

	@XmlAttribute
	protected String rule;

	@XmlAttribute
	protected Short genEveryDay = Short.valueOf((short) 1);

	@XmlAttribute
	protected Integer noLength;

	@XmlAttribute
	protected Integer initValue;

	@XmlAttribute
	protected Integer curValue;
	protected String curDate = "";

	@XmlAttribute
	protected Short step = Short.valueOf((short) 1);

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getRule() {
		return this.rule;
	}

	public void setGenEveryDay(Short genEveryDay) {
		this.genEveryDay = genEveryDay;
	}

	public Short getGenEveryDay() {
		return this.genEveryDay;
	}

	public void setNoLength(Integer noLength) {
		this.noLength = noLength;
	}

	public Integer getNoLength() {
		return this.noLength;
	}

	public void setInitValue(Integer initValue) {
		this.initValue = initValue;
	}

	public Integer getInitValue() {
		return this.initValue;
	}

	public void setCurValue(Integer curValue) {
		this.curValue = curValue;
	}

	public Integer getCurValue() {
		return this.curValue;
	}

	public Short getStep() {
		return this.step;
	}

	public void setStep(Short step) {
		this.step = step;
	}

	public String getCurDate() {
		return this.curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Identity)) {
			return false;
		}
		Identity rhs = (Identity) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.name, rhs.name).append(this.alias, rhs.alias)
				.append(this.rule, rhs.rule).append(this.genEveryDay, rhs.genEveryDay)
				.append(this.noLength, rhs.noLength).append(this.initValue, rhs.initValue)
				.append(this.curValue, rhs.curValue).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.name).append(this.alias)
				.append(this.rule).append(this.genEveryDay).append(this.noLength).append(this.initValue)
				.append(this.curValue).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("alias", this.alias)
				.append("rule", this.rule).append("genEveryDay", this.genEveryDay).append("noLength", this.noLength)
				.append("initValue", this.initValue).append("curValue", this.curValue).toString();
	}
}
