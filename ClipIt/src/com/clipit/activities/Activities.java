package com.clipit.activities;

import java.io.Serializable;

public class Activities implements Serializable{
	public String nameActivities;
	public String description;
	public String status;
	public String initialTime;
	public String finalTime;
	
	
	
	public String getNameActivities() {
		return nameActivities;
	}
	public void setNameActivities(String nameActivities) {
		this.nameActivities = nameActivities;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInitialTime() {
		return initialTime;
	}
	public void setInitialTime(String initialTime) {
		this.initialTime = initialTime;
	}
	public String getFinalTime() {
		return finalTime;
	}
	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}
	
}
