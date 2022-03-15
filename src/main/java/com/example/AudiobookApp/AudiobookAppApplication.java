
package com.example.AudiobookApp;

import java.util.ArrayList;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.AudiobookApp.model.Audiobook;
import com.example.AudiobookApp.model.AudiobookRepository;
import com.example.AudiobookApp.model.User;
import com.example.AudiobookApp.model.UserRepository;


@SpringBootApplication
public class AudiobookAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AudiobookAppApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(AudiobookRepository audiobookRepository, UserRepository userRepository) {
		return args->{
			ArrayList<String> authors = new ArrayList<>();
			ArrayList<String> categories = new ArrayList<>();
			ArrayList<String> favourites = new ArrayList<>();

			authors.add("testAuthor");
			authors.add("testAuthor2");
			categories.add("comedy");
			categories.add("fantasy");
			Audiobook audiobook1 = new Audiobook("Test Audiobook Title1", authors, categories);
			Audiobook audiobook2 = new Audiobook("Test Audiobook Title2", authors, categories);
			User[] users = {
					new User("user1","pass1",0,favourites),
					new User("user2","pass2",1,favourites)

			};
			
			audiobookRepository.save(audiobook1);
			audiobookRepository.save(audiobook2);

			users[0].addFavourite(audiobook1);
			users[0].addFavourite(audiobook2);
			users[1].addFavourite(audiobook1);
			
			for (int i = 0; i < users.length; i++) {
				userRepository.save(users[i]);
			}
			
		};
	}

}
