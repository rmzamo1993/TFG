package com.clipit.chat;

public class Message {
	
	boolean isMine;
	/**
	 * boolean to determine, whether the message is a status message or not.
	 * it reflects the changes/updates about the sender is writing, have entered text etc
	 */
	boolean isStatusMessage;
	private String destination;
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	private String message;
	private String title;
	private String timeCreated;
	
	/**
	 * Constructor to make a Message object
	 */
	public Message(String message, boolean isMine) {
		super();
		this.message = message;
		this.isMine = isMine;
		this.isStatusMessage = false;
	}
	/**
	 * Constructor to make a status Message object
	 * consider the parameters are swaped from default Message constructor,
	 *  not a good approach but have to go with it.
	 */
	public Message(boolean status, String message) {
		super();
		this.message = message;
		this.isMine = false;
		this.isStatusMessage = status;
	}
	
	public Message() {
		super();
		this.message = "";
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String menssage) {
		this.message = menssage;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}
	public boolean isStatusMessage() {
		return isStatusMessage;
	}
	public void setStatusMessage(boolean isStatusMessage) {
		this.isStatusMessage = isStatusMessage;
	}
	
	public boolean isMine() {
		return isMine;
	}
	
	
}
