package com.company.tetris.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.tetris.dto.GameState;
import com.company.tetris.model.Room;
import com.company.tetris.model.User;
import com.company.tetris.service.RoomService;
import com.company.tetris.service.TetrisService;

@Controller @RequestMapping("game")
public class TetrisController {
	@Autowired private TetrisService tetrisService;
	@Autowired private RoomService roomService;
	
	@RequestMapping("/") @ResponseBody
	public String basic() {return "/";}
	
	@GetMapping("/home")
	public String gamaPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loginUser");
        if (user == null) { return "redirect:/user/login"; }
		
		model.addAttribute("score", tetrisService.getScore());
		return "game/single";
	}
	
	@GetMapping("/multi")
	public String multiPage(@RequestParam String roomId, Model model, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		if(user == null) { return "redirect:/user/login"; }
		
		Room room = roomService.getRoom(roomId);
	    List<User> participants = room != null  ? new ArrayList<>(room.getParticipants()) : new ArrayList<>();

	    model.addAttribute("roomId", roomId);
	    model.addAttribute("userID", user.getUserId());
	    model.addAttribute("participants", participants);
		return "game/multi";
	}
	
	@GetMapping("/lobby")
	public String multiPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loginUser");
        if (user == null) { return "redirect:/user/login"; }
        
        model.addAttribute("userId", user.getUserId());
		return "game/lobby";
	}
	
	@ResponseBody
    @GetMapping("/state")
    public GameState getState() { return tetrisService.getCurrentState(); }

    @ResponseBody
    @PostMapping("/move")
    public void move(@RequestParam String direction) { tetrisService.move(direction); }

    @ResponseBody
    @PostMapping("/drop")
    public void drop() { tetrisService.drop(); }

    @ResponseBody
    @PostMapping("/spawn")
    public void spawn() { tetrisService.spawn(); }
    
    @ResponseBody
    @PostMapping("/pause")
    public void pause() {tetrisService.pause();}
    
    @ResponseBody
    @PostMapping("/resume")
    public void resume() {tetrisService.resume();}
    
    @ResponseBody
    @PostMapping("/restart")
    public void restart() {tetrisService.restart();}
}
