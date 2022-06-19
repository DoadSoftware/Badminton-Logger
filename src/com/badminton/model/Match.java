package com.badminton.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.List;

import javax.persistence.Column;

@Entity
@Table(name = "Match")
public class Match {

  @Id
  @Column(name = "matchId")
  private Integer matchId;
	
  @Column(name = "homeFirstPlayerId")
  private Integer homeFirstPlayerId ;
  
  @Column(name = "homeSecondPlayerId")
  private Integer homeSecondPlayerId ;

  @Column(name = "homeThirdPlayerId")
  private Integer homeThirdPlayerId ;
  
  @Column(name = "awayFirstPlayerId")
  private Integer awayFirstPlayerId;
  
  @Column(name = "awaySecondPlayerId")
  private Integer awaySecondPlayerId;

  @Column(name = "awayThirdPlayerId")
  private Integer awayThirdPlayerId;
  
  @Column(name = "numberOfSets")
  private Integer numberOfSets;

  @Transient
  private Team homeTeam;

  @Transient
  private Team awayTeam;
  
  @Transient
  private List<Player> homePlayers;

  @Transient
  private List<Player> awayPlayers;
  
public Match(Integer matchId) {
	super();
	this.matchId = matchId;
}

public Team getHomeTeam() {
	return homeTeam;
}

public void setHomeTeam(Team homeTeam) {
	this.homeTeam = homeTeam;
}

public Team getAwayTeam() {
	return awayTeam;
}

public void setAwayTeam(Team awayTeam) {
	this.awayTeam = awayTeam;
}

public Match() {
	super();
}

public Integer getMatchId() {
	return matchId;
}

public void setMatchId(Integer matchId) {
	this.matchId = matchId;
}

public Integer getHomeFirstPlayerId() {
	return homeFirstPlayerId;
}

public void setHomeFirstPlayerId(Integer homeFirstPlayerId) {
	this.homeFirstPlayerId = homeFirstPlayerId;
}

public Integer getHomeSecondPlayerId() {
	return homeSecondPlayerId;
}

public void setHomeSecondPlayerId(Integer homeSecondPlayerId) {
	this.homeSecondPlayerId = homeSecondPlayerId;
}

public Integer getHomeThirdPlayerId() {
	return homeThirdPlayerId;
}

public void setHomeThirdPlayerId(Integer homeThirdPlayerId) {
	this.homeThirdPlayerId = homeThirdPlayerId;
}

public Integer getAwayFirstPlayerId() {
	return awayFirstPlayerId;
}

public void setAwayFirstPlayerId(Integer awayFirstPlayerId) {
	this.awayFirstPlayerId = awayFirstPlayerId;
}

public Integer getAwaySecondPlayerId() {
	return awaySecondPlayerId;
}

public void setAwaySecondPlayerId(Integer awaySecondPlayerId) {
	this.awaySecondPlayerId = awaySecondPlayerId;
}

public Integer getAwayThirdPlayerId() {
	return awayThirdPlayerId;
}

public void setAwayThirdPlayerId(Integer awayThirdPlayerId) {
	this.awayThirdPlayerId = awayThirdPlayerId;
}

public Integer getNumberOfSets() {
	return numberOfSets;
}

public void setNumberOfSets(Integer numberOfSets) {
	this.numberOfSets = numberOfSets;
}

public List<Player> getHomePlayers() {
	return homePlayers;
}

public void setHomePlayers(List<Player> homePlayers) {
	this.homePlayers = homePlayers;
}

public List<Player> getAwayPlayers() {
	return awayPlayers;
}

public void setAwayPlayers(List<Player> awayPlayers) {
	this.awayPlayers = awayPlayers;
}

}