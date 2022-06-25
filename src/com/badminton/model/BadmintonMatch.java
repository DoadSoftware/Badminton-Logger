package com.badminton.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@XmlRootElement(name="match")
@XmlAccessorType(XmlAccessType.FIELD)
public class BadmintonMatch {

  @XmlElement
  private Match match;
  
  @XmlElement
  private int onStrikePlayerId;
  
  @XmlElement
  private int goldenPointsPlayerId;

  @XmlElement
  private int homeTeamSetsWon;

  @XmlElement
  private int awayTeamSetsWon;
 
  @XmlElementWrapper(name = "stats")
  @XmlElement(name = "stat")
  private List<Stats> stats;

  @XmlElementWrapper(name = "sets")
  @XmlElement(name = "set")
  private List<Set> sets;
  
  @XmlTransient
  private String database_file_timestamp;
  
  @XmlTransient
  private List<Match> matches;
  
public BadmintonMatch(Match match) {
	super();
	this.match = match;
}

public int getHomeTeamSetsWon() {
	return homeTeamSetsWon;
}

public void setHomeTeamSetsWon(int homeTeamSetsWon) {
	this.homeTeamSetsWon = homeTeamSetsWon;
}

public int getAwayTeamSetsWon() {
	return awayTeamSetsWon;
}

public void setAwayTeamSetsWon(int awayTeamSetsWon) {
	this.awayTeamSetsWon = awayTeamSetsWon;
}

public BadmintonMatch() {
	super();
}

public List<Set> getSets() {
	return sets;
}

public void setSets(List<Set> sets) {
	this.sets = sets;
}

public List<Match> getMatches() {
	return matches;
}

public void setMatches(List<Match> matches) {
	this.matches = matches;
}

public Match getMatch() {
	return match;
}

public void setMatch(Match match) {
	this.match = match;
}

public List<Stats> getStats() {
	return stats;
}

public void setStats(List<Stats> stats) {
	this.stats = stats;
}

public String getDatabase_file_timestamp() {
	return database_file_timestamp;
}

public void setDatabase_file_timestamp(String database_file_timestamp) {
	this.database_file_timestamp = database_file_timestamp;
}

public int getOnStrikePlayerId() {
	return onStrikePlayerId;
}

public void setOnStrikePlayerId(int onStrikePlayerId) {
	this.onStrikePlayerId = onStrikePlayerId;
}

public int getGoldenPointsPlayerId() {
	return goldenPointsPlayerId;
}

public void setGoldenPointsPlayerId(int goldenPointsPlayerId) {
	this.goldenPointsPlayerId = goldenPointsPlayerId;
}

}