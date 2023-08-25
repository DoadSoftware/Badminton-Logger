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
@Table(name = "Groups")
public class Group {

  @Id
  @Column(name = "GroupId")
  private int groupId;

  @Column(name = "GroupName")
  private String groupname;

public Group() {
	super();
}

public Group(int groupId) {
	super();
	this.groupId = groupId;
}

public int getGroupId() {
	return groupId;
}

public void setGroupId(int groupId) {
	this.groupId = groupId;
}

public String getGroupname() {
	return groupname;
}

public void setGroupname(String groupname) {
	this.groupname = groupname;
}


}

