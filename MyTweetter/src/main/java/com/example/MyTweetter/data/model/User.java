package com.example.MyTweetter.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.MyTweetter.utilities.Utility;

@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	@Column(nullable = true)
	private String name;
	@Column(nullable = true)
	private String surname;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false, unique = true)
	private String email;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Tweet> tweets;
	
	public User() {}
	public User(String email, String password, String name, String surname) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
	}
	public User clone () {
		return clone(false);
	}
	public User clone (boolean clearTweets) {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setIdUser(idUser);
		newUser.setName(name);
		newUser.setPassword(password);
		newUser.setSurname(surname);
		if (!clearTweets) {
			List<Tweet> newTweets = new ArrayList<Tweet>(); 
			for (Tweet tweet : tweets) {
				newTweets.add(tweet.clone(true , true));
			}
			newUser.setTweets(newTweets);			
		}
		return newUser;
	}
	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", surname=" + surname + ", email=" + email + ", tweets="
				+ tweets + "]";
	}
	public boolean isValid() {
		return Utility.checkEmail(this.email) && this.getPassword() != null;
	}
	public List<Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
