package com.example.AudiobookApp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;





@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	//0 - admin, 1 - user
	@Column(name = "permission")
	private int permission;
	
	@Column(name = "favourites")
	private ArrayList<String> favourites = new ArrayList<>();
	
	
	@ManyToMany
	@JoinTable(
	  name = "user_favourites", 
	  joinColumns = @JoinColumn(name = "user"), 
	  inverseJoinColumns = @JoinColumn(name = "audiobook"))
	Set<Audiobook> favouriteAudiobooks = new HashSet<Audiobook>();



	public User() {
		
	}
	
	
	
	public User(String username,String password, int permission, ArrayList<String> favourites) {
		this.username=username;
		this.password=password;
		this.permission=permission;
		this.favourites = (ArrayList<String>) favourites.clone();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public int  getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public ArrayList<String> getFavourites() {
		return favourites;
	}
	
	public Set<Audiobook> getAudiobooks(){
		return favouriteAudiobooks;
	}

	public void setFavourites(ArrayList<String> favourites) {
		this.favourites = favourites;
	}
	public void addFavourite(Audiobook audiobook) {
		String title = audiobook.getTitle();
		this.favouriteAudiobooks.add(audiobook);
//		audiobook.getUsers().add(this);
	}

	public void removeCourse(Audiobook audiobook) {
		audiobook.getUsers().remove(this);
		this.favourites.remove(audiobook);
	}

}
