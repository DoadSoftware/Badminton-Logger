package com.badminton.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sets")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sets {

  @XmlElement(name = "setsNumber")
  private int setsNumber;

  @XmlElement(name = "homePlayerId")
  private int homePlayerId;
  
  @XmlElement(name = "awayPlayerId")
  private int awayPlayerId;

  @XmlElementWrapper(name = "stats")
  @XmlElement(name = "stat")
  private List<Stats> stats;

public int getSetsNumber() {
	return setsNumber;
}

public void setSetsNumber(int setsNumber) {
	this.setsNumber = setsNumber;
}

public int getHomePlayerId() {
	return homePlayerId;
}

public void setHomePlayerId(int homePlayerId) {
	this.homePlayerId = homePlayerId;
}

public int getAwayPlayerId() {
	return awayPlayerId;
}

public void setAwayPlayerId(int awayPlayerId) {
	this.awayPlayerId = awayPlayerId;
}

public List<Stats> getStats() {
	return stats;
}

public void setStats(List<Stats> stats) {
	this.stats = stats;
}
  
}