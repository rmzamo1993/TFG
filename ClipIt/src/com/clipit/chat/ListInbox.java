package com.clipit.chat;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class ListInbox implements Serializable {
	private String idUserSender;
	private String name;
	private int imagen;
	private String lastMessage;
	private String loginUser;
	
	
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	public String getIdUserSender() {
		return idUserSender;
	}
	public void setIdUserSender(String idUserSender) {
		this.idUserSender = idUserSender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImagen() {
		return imagen;
	}
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}
	
	
	
	
	
}
