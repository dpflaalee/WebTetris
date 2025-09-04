package com.company.tetris.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.tetris.model.User;
import com.company.tetris.service.UserService;

@Controller
@RequestMapping("user")

public class UserController {
	
	private final UserService userService;
	
	//final 필드 초기화
	public UserController(UserService userService) {this.userService = userService;}
	
	//로그인
	@GetMapping("/login")
	public String loginForm() {return "user/login";}
	@PostMapping("/login")
	public String login(@RequestParam String userId, @RequestParam String password, Model model, HttpSession session) {
		if (userService.validateUser(userId, password)) {
            User user = userService.getUser(userId);
            session.setAttribute("loginUser", user);
            return "redirect:/user/home";
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "user/login";
        }
	}
	
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) { return "redirect:/user/login"; }
        model.addAttribute("name", user.getName());
        return "user/home";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
	
}
