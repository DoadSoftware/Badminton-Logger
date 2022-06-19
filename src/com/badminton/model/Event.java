package com.badminton.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event {

	@XmlElement(name = "eventNumber")
	private int eventNumber;
	
	@XmlElement(name = "eventType")
	private String eventType;

	@XmlElement(name = "eventPlayerId")
	private int eventPlayerId;

	@XmlElement(name = "eventOtherPlayerId")
	private int eventOtherPlayerId;
	
	@XmlElement(name = "eventValue")
	private int eventValue;

	@XmlElement(name = "eventTotalEventScore")
	private int eventTotalEventScore;

	public int getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(int eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public int getEventPlayerId() {
		return eventPlayerId;
	}

	public void setEventPlayerId(int eventPlayerId) {
		this.eventPlayerId = eventPlayerId;
	}

	public int getEventOtherPlayerId() {
		return eventOtherPlayerId;
	}

	public void setEventOtherPlayerId(int eventOtherPlayerId) {
		this.eventOtherPlayerId = eventOtherPlayerId;
	}

	public int getEventValue() {
		return eventValue;
	}

	public void setEventValue(int eventValue) {
		this.eventValue = eventValue;
	}

	public int getEventTotalEventScore() {
		return eventTotalEventScore;
	}

	public void setEventTotalEventScore(int eventTotalEventScore) {
		this.eventTotalEventScore = eventTotalEventScore;
	}

}