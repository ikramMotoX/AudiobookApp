package com.example.AudiobookApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AudiobookApp.model.Audiobook;
import com.example.AudiobookApp.model.User;
import com.example.AudiobookApp.model.UserRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUserss(@RequestParam(required = false)String title){
		try {
			List<User> users = new ArrayList<User>();
			if(title==null) {
				userRepository.findAll().forEach(users::add);
			}else {
				userRepository.findByUsernameContainingIgnoreCase(title).forEach(users::add);
			}
			if(users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
				return new ResponseEntity<>(users,HttpStatus.OK);
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createAudiobook(@RequestBody User user){
		try {
			User _user = userRepository.save(new User(user.getUsername(),user.getPassword(),user.getPermission(),user.getFavourites()));
				return new ResponseEntity<>(_user,HttpStatus.CREATED); 
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id")long id, @RequestBody User user){
		Optional<User> userData = userRepository.findById(id);
		
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setUsername(user.getUsername());
			_user.setPassword(user.getPassword());
			_user.setPermission(user.getPermission());
			_user.setFavourites(user.getFavourites());
			return new ResponseEntity<>(userRepository.save(_user),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/user/{id}")
	public ResponseEntity<HttpStatus> deleteAudiobook(@PathVariable("id")long id){
		
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			// TODO: handle exception
		}
	}
	@DeleteMapping("/user")
	public ResponseEntity<HttpStatus> deleteAllUsers(){
		try {
			userRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
