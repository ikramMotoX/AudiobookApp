package com.example.AudiobookApp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="audiobook")
public class Audiobook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@Column(name = "title")
	private String title;

	@Column(name = "authors")
	private ArrayList<String> authors = new ArrayList<>();

	@Column(name = "categories")
	private ArrayList<String> categories = new ArrayList<>();
		
	
	@JsonIgnore // To avoid from infinite recrussion when creating json object
	// because course has a collection of students and a student has a collection of
	// course
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_favourites", joinColumns = { // owner entity
	@JoinColumn(name = "audiobook", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "user", referencedColumnName = "id") })
	private Set<User> users = new HashSet<>();
	
	public Audiobook() {
		
	}
	

	
	public Set<User> getUsers() {
		return users;
	}



	public void setUsers(Set<User> users) {
		this.users = users;
	}



	public Audiobook(String title,ArrayList<String>authors,ArrayList<String>categories) {
		this.title= title;
		this.authors = (ArrayList<String>) authors.clone();
		this.categories = (ArrayList<String>) categories.clone();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}

	public ArrayList<String> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

}
