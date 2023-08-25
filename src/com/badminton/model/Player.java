package com.badminton.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;

@Entity
@Table(name = "Players")
public class Player
{
  @Id
  @Column(name = "PlayerID")
  private int playerId;
	
  @Column(name = "FullName")
  private String full_name;

  @Column(name = "Tickername")
  private String ticker_name;
  
  @Column(name = "TickerSMname")
  private String ticker_sm_name;

  @Column(name = "TeamId")
  private int teamId;
  
  @Column(name = "Age")
  private String age;
  
  @Column(name = "Text1")
  private String text1;
  
  @Column(name = "Text2")
  private String text2;
  
  @Column(name = "BAIRanking")
  private Integer baiRanking;
  
  @Column(name = "BWFRanking")
  private Integer bwfRanking;
  
  @Column(name = "IconPlayer")
  private String iconPlayer;
  
  @Column(name = "OverseasPlayer")
  private String overseasPlayer;
  
  @Column(name = "PlayerPhotoNames")
  private String playerPhotoNames;
  
  @Transient
  private Team team;

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

public String getTicker_sm_name() {
	return ticker_sm_name;
}

public void setTicker_sm_name(String ticker_sm_name) {
	this.ticker_sm_name = ticker_sm_name;
}

public int getTeamId() {
	return teamId;
}

public void setTeamId(int teamId) {
	this.teamId = teamId;
}

public String getAge() {
	return age;
}

public void setAge(String age) {
	this.age = age;
}

public String getText1() {
	return text1;
}

public void setText1(String text1) {
	this.text1 = text1;
}

public String getText2() {
	return text2;
}

public void setText2(String text2) {
	this.text2 = text2;
}

public Integer getBaiRanking() {
	return baiRanking;
}

public void setBaiRanking(Integer baiRanking) {
	this.baiRanking = baiRanking;
}

public Integer getBwfRanking() {
	return bwfRanking;
}

public void setBwfRanking(Integer bwfRanking) {
	this.bwfRanking = bwfRanking;
}

public String getIconPlayer() {
	return iconPlayer;
}

public void setIconPlayer(String iconPlayer) {
	this.iconPlayer = iconPlayer;
}

public String getOverseasPlayer() {
	return overseasPlayer;
}

public void setOverseasPlayer(String overseasPlayer) {
	this.overseasPlayer = overseasPlayer;
}

public String getPlayerPhotoNames() {
	return playerPhotoNames;
}

public void setPlayerPhotoNames(String playerPhotoNames) {
	this.playerPhotoNames = playerPhotoNames;
}

public Team getTeam() {
	return team;
}

public void setTeam(Team team) {
	this.team = team;
}

@Override
public String toString() {
	return "Player [playerId=" + playerId + ", full_name=" + full_name + ", ticker_name=" + ticker_name
			+ ", ticker_sm_name=" + ticker_sm_name + ", teamId=" + teamId + ", age=" + age + ", text1=" + text1
			+ ", text2=" + text2 + ", baiRanking=" + baiRanking + ", bwfRanking=" + bwfRanking + ", iconPlayer="
			+ iconPlayer + ", overseasPlayer=" + overseasPlayer + ", playerPhotoNames=" + playerPhotoNames + ", team="
			+ team + "]";
}

}