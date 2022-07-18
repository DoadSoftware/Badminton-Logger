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
@Table(name = "TeamColors")
public class TeamColor {

  @Id
  @Column(name = "TEAMCOLORID")
  private int teamcolorId;

  @Column(name = "COLORNAME")
  private String colorname;

  @Column(name = "TEAMCOLOR")
  private String teamcolor;

public TeamColor() {
	super();
}

public TeamColor(int teamcolorId) {
	super();
	this.teamcolorId = teamcolorId;
}

public int getTeamcolorId() {
	return teamcolorId;
}

public void setTeamcolorId(int teamcolorId) {
	this.teamcolorId = teamcolorId;
}

public String getColorname() {
	return colorname;
}

public void setColorname(String colorname) {
	this.colorname = colorname;
}

public String getTeamcolor() {
	return teamcolor;
}

public void setTeamcolor(String teamcolor) {
	this.teamcolor = teamcolor;
}

}

