package com.badminton.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;


@SuppressWarnings("unused")
@Entity
@Table(name = "Rules")
public class Rules
{
  @Id
  @Column(name = "RuleId")
  private int ruleId;

  @Column(name = "TEXT1")
  private String text1;

  public Rules() {
	  super();
  }

public Rules(int ruleId) {
	super();
	this.ruleId = ruleId;
}

public int getRuleId() {
	return ruleId;
}

public void setRuleId(int ruleId) {
	this.ruleId = ruleId;
}

public String getText1() {
	return text1;
}

public void setText1(String text1) {
	this.text1 = text1;
}

}