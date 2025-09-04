package com.company.tetris.model;

public class User {
	private String name;
	private String userId;
	private String password;
	
	//대기실 준비상태
	private boolean ready = false;
	public User(String userId) {
		this.userId = userId;
		this.name = userId;
		this.password = "";
	}
	
	public User(String name, String userId, String password) {
		this.name = name;
		this.userId = userId;
		this.password = password;
	}

	public String getName() { return name; }
	public String getUserId() { return userId; }
	public String getPassword() { return password; }
	
	public boolean isReady() {return ready;}
	public void setReady(boolean ready) {this.ready = ready;}
}
/*
 * 지금은 기능만 간단하게 필요한 것 같아서 구현 안 하긴 했는데... 추가 확장 필요하면 implements Serializable 추가해야 함.
 * 
 */