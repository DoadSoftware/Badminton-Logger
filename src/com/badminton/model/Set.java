package com.badminton.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sets")
@XmlAccessorType(XmlAccessType.FIELD)
public class Set {

  @XmlElement(name = "setNumber")
  private int setNumber;

  @XmlElement(name = "status")
  private String status;
  
  @XmlElement(name = "winningTeamId")
  private int winningTeamId;

  @XmlElement(name = "homeTeamTotalScore")
  private int homeTeamTotalScore;

  @XmlElement(name = "awayTeamTotalScore")
  private int awayTeamTotalScore;
  
  @XmlElementWrapper(name = "homePlayers")
  @XmlElement(name = "homePlayer")
  private List<Player> homePlayers;

  @XmlElementWrapper(name = "awayPlayers")
  @XmlElement(name = "awayPlayer")
  private List<Player> awayPlayers;
  
  @XmlElementWrapper(name = "stats")
  @XmlElement(name = "stat")
  private List<Stats> stats;

public Set() {
	super();
}

public Set(int setNumber, String status, List<Player> homePlayers, List<Player> awayPlayers, List<Stats> stats) {
	super();
	this.setNumber = setNumber;
	this.status = status;
	this.homePlayers = homePlayers;
	this.awayPlayers = awayPlayers;
	this.stats = stats;
}

public int getHomeTeamTotalScore() {
	return homeTeamTotalScore;
}

public void setHomeTeamTotalScore(int homeTeamTotalScore) {
	this.homeTeamTotalScore = homeTeamTotalScore;
}

public int getAwayTeamTotalScore() {
	return awayTeamTotalScore;
}

public void setAwayTeamTotalScore(int awayTeamTotalScore) {
	this.awayTeamTotalScore = awayTeamTotalScore;
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

public int getSetNumber() {
	return setNumber;
}

public void setSetNumber(int setNumber) {
	this.setNumber = setNumber;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public int getWinningTeamId() {
	return winningTeamId;
}

public void setWinningTeamId(int winningTeamId) {
	this.winningTeamId = winningTeamId;
}

public List<Stats> getStats() {
	return stats;
}

public void setStats(List<Stats> stats) {
	this.stats = stats;
}

}