package com.badminton.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;

@Entity
@Table(name = "Match")
public class Match {

  @Id
  @Column(name = "matchId")
  private int matchId;
	
  @Column(name = "homeFirstPlayer")
  private Integer homeFirstPlayer ;
  
  @Column(name = "homeSecondPlayer")
  private Integer homeSecondPlayer ;

  @Column(name = "awayFirstPlayer")
  private Integer awayFirstPlayer;
  
  @Column(name = "awaySecondPlayer")
  private Integer awaySecondPlayer;

  @Column(name = "numberOfSets")
  private int numberOfSets;
  
  @Transient
  private Player homePlayer;

  @Transient
  private Player awayPlayer;
  
  @Transient
  private Player homePlayer1;

  @Transient
  private Player awayPlayer1;

public Match(int matchId) {
	super();
	this.matchId = matchId;
}

public Match() {
	super();
}

public int getMatchId() {
	return matchId;
}

public void setMatchId(int matchId) {
	this.matchId = matchId;
}

public Integer getHomeFirstPlayer() {
	return homeFirstPlayer;
}

public void setHomeFirstPlayer(Integer homeFirstPlayer) {
	this.homeFirstPlayer = homeFirstPlayer;
}

public Integer getHomeSecondPlayer() {
	return homeSecondPlayer;
}

public void setHomeSecondPlayer(Integer homeSecondPlayer) {
	this.homeSecondPlayer = homeSecondPlayer;
}

public Integer getAwayFirstPlayer() {
	return awayFirstPlayer;
}

public void setAwayFirstPlayer(Integer awayFirstPlayer) {
	this.awayFirstPlayer = awayFirstPlayer;
}

public Integer getAwaySecondPlayer() {
	return awaySecondPlayer;
}

public void setAwaySecondPlayer(Integer awaySecondPlayer) {
	this.awaySecondPlayer = awaySecondPlayer;
}

public int getNumberOfSets() {
	return numberOfSets;
}

public void setNumberOfSets(int numberOfSets) {
	this.numberOfSets = numberOfSets;
}

public Player getHomePlayer() {
	return homePlayer;
}

public void setHomePlayer(Player homePlayer) {
	this.homePlayer = homePlayer;
}

public Player getAwayPlayer() {
	return awayPlayer;
}

public void setAwayPlayer(Player awayPlayer) {
	this.awayPlayer = awayPlayer;
}

public Player getHomePlayer1() {
	return homePlayer1;
}

public void setHomePlayer1(Player homePlayer1) {
	this.homePlayer1 = homePlayer1;
}

public Player getAwayPlayer1() {
	return awayPlayer1;
}

public void setAwayPlayer1(Player awayPlayer1) {
	this.awayPlayer1 = awayPlayer1;
}

@Override
public String toString() {
	return "Match [matchId=" + matchId + ", homeFirstPlayer=" + homeFirstPlayer + ", homeSecondPlayer=" + homeSecondPlayer +
			", awayFirstPlayer=" + awayFirstPlayer + ", awaySecondPlayer=" + awaySecondPlayer + ", numberOfSets=" + numberOfSets +
			 ", homePlayer=" + homePlayer  + ", awayPlayer=" + awayPlayer + ", homePlayer1=" + homePlayer1  + ", awayPlayer1=" + awayPlayer1 +"]";
}
  
}