package com.company.tetris.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.tetris.model.User;

@Service
public class UserService {
	private final Map<String, User> userMap = new HashMap<>();
	
	public UserService() {
		userMap.put("nuts", new User("헤넛","nuts", "1234"));
		userMap.put("hkjung", new User("hkjung", "hkjung", "1234"));
		userMap.put("yskim", new User("yskim", "yskim", "1234"));
		userMap.put("gbcheon", new User("gbcheon", "gbcheon", "1234"));
		userMap.put("yllee", new User("yllee", "yllee", "1234"));
	}
	
	//로그인 성공 시 데이터 반환
	public boolean validateUser(String userId, String password){
		User user = userMap.get(userId);
		return user != null && user.getPassword().equals(password);
	}

	public User getUser(String userId){return userMap.get(userId);}
}
