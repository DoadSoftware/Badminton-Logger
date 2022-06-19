package com.badminton.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.persistence.Column;

@Entity
@Table(name = "Players")
public class Player
{

  @Id
  @Column(name = "PLAYERID")
  private int playerId;
	
  @Column(name = "FULLNAME")
  private String full_name;

  @Column(name = "TICKERNAME")
  private String ticker_name;

  @Column(name = "TEAMID")
  private int teamId;
  
  @Transient
  private Team team;
  
  @Column(name = "Age")
  private int age;
  
  @Column(name = "BWKRanking")
  private String BWKRanking;
  
  @Column(name = "PlayerInfo1")
  private String Player_Info1;
  
  @Column(name = "PlayerInfo2")
  private String Player_Info2;
  
  @Column(name = "PlayerInfo3")
  private String Player_Info3;

public int getPlayerId() {
	return playerId;
}

public void setPlayerId(int playerId) {
	this.playerId = playerId;
}

public String getFull_name() {
	return full_name;
}

public void setFull_name(String full_name) {
	this.full_name = full_name;
}

public String getTicker_name() {
	return ticker_name;
}

public void setTicker_name(String ticker_name) {
	this.ticker_name = ticker_name;
}

public int getTeamId() {
	return teamId;
}

public void setTeamId(int teamId) {
	this.teamId = teamId;
}

public Team getTeam() {
	return team;
}

public void setTeam(Team team) {
	this.team = team;
}

@Override
public String toString() {
	return "Player [playerId=" + playerId + ", full_name=" + full_name + ", ticker_name=" + ticker_name + ", teamId=" + teamId
			+ ", team=" + team + "]";
}
 
}