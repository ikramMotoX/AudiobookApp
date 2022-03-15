package com.example.AudiobookApp.controller;

import java.security.PublicKey;
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
import com.example.AudiobookApp.model.AudiobookRepository;



@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class AudiobookController {
	@Autowired
	AudiobookRepository audiobookRepository;

	@GetMapping("/audiobooks/{id}")
	public ResponseEntity<Audiobook> getAudiobookById(@PathVariable("id") long id) {
		Optional<Audiobook> audiobookData = audiobookRepository.findById(id);

		if (audiobookData.isPresent()) {
			return new ResponseEntity<>(audiobookData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/audiobooks/{title}")
	public ResponseEntity<List<Audiobook>> getAudiobookById(@PathVariable("title") String title) {
		List<Audiobook> audiobooks = new ArrayList<Audiobook>();

		if (title!=null) {
			audiobookRepository.findByTitleContainingIgnoreCase(title).forEach(audiobooks::add);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(audiobooks,HttpStatus.OK);

	}
	
	@GetMapping("/audiobooks")
	public ResponseEntity<List<Audiobook>> getAudiobooks(@RequestParam(required = false)String title){
		try {
			List<Audiobook> audiobooks = new ArrayList<Audiobook>();
			if(title==null) {
				audiobookRepository.findAll().forEach(audiobooks::add);
			}else {
				audiobookRepository.findByTitleContainingIgnoreCase(title).forEach(audiobooks::add);
			}
			if(audiobooks.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
				return new ResponseEntity<>(audiobooks,HttpStatus.OK);
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/audiobooks")
	public ResponseEntity<Audiobook> createAudiobook(@RequestBody Audiobook audiobook){
		try {
			Audiobook _audiobook = audiobookRepository.save(new Audiobook(audiobook.getTitle(),audiobook.getAuthors(),audiobook.getCategories()));
				return new ResponseEntity<>(_audiobook,HttpStatus.CREATED); 
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/audiobooks/{id}")
	public ResponseEntity<Audiobook> updateAudiobook(@PathVariable("id")long id, @RequestBody Audiobook audiobook){
		Optional<Audiobook> audiobookData = audiobookRepository.findById(id);
		
		if (audiobookData.isPresent()) {
			Audiobook _audiobook = audiobookData.get();
			_audiobook.setTitle(audiobook.getTitle());
			_audiobook.setAuthors(audiobook.getAuthors());
			_audiobook.setCategories(audiobook.getCategories());
			return new ResponseEntity<>(audiobookRepository.save(_audiobook),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/audiobooks/{id}")
	public ResponseEntity<HttpStatus> deleteAudiobook(@PathVariable("id")long id){
		
		try {
			audiobookRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			// TODO: handle exception
		}
	}
	@DeleteMapping("/audiobooks")
	public ResponseEntity<HttpStatus> deleteAllAudioBooks(){
		try {
			audiobookRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
