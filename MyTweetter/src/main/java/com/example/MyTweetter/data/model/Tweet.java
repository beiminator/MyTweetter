package com.example.MyTweetter.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Table;

@Entity
@Table(name = "tweet")
public class Tweet implements Comparable<Tweet>{
	
	public static final int TEXT_LENGTH = 140;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tweet")
	private Long idTweet;
	
	@Column(nullable = false, length = TEXT_LENGTH)
	private String text;
	
	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate;
	
	@ManyToMany(targetEntity = HashTag.class, fetch = FetchType.LAZY)
	@JoinTable(
			name = "tweet_hashtag",
			joinColumns = @JoinColumn(name = "id_tweet"),
			inverseJoinColumns = @JoinColumn(name = "id_hashtag")
			)
	private List<HashTag> hashTags;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	public Tweet() {}
	public Tweet(String text) {
		this.text = text;
		this.creationDate = LocalDateTime.now();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		Tweet tObj = (Tweet) obj;
		if (obj!=null && this.idTweet == tObj.idTweet) {
			ret = true;
		}
		return ret;
	}
	
	
	
	public Tweet clone(boolean cleanHashTags, boolean clearUser) {
		Tweet newTweet = new Tweet();
		newTweet.setCreationDate(creationDate);
		newTweet.setText(text);
		newTweet.setIdTweet(idTweet);
		if (!clearUser) {
			newTweet.setUser(user.clone(true));			
		}
		
		if (!cleanHashTags) {
			List<HashTag> newHashTags = new ArrayList<HashTag>();
			for (HashTag hashTag : this.hashTags) {
				newHashTags.add(hashTag.clone(true));
			}
			newTweet.setHashTags(newHashTags);
		}
		
		return newTweet;
	}
	public void cleanHashTagPointers() {
		for (HashTag hashTag : hashTags) {
			hashTag.setTweets(null);
		}
	}
	
	@Override
	public String toString() {
		return "Tweet [idTweet=" + idTweet + ", text=" + text + ", creationDate=" + creationDate + ", hashTags="
				+ hashTags + "]";
	}

	public Long getIdTweet() {
		return idTweet;
	}

	public void setIdTweet(Long idTweet) {
		this.idTweet = idTweet;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public List<HashTag> getHashTags() {
		return hashTags;
	}

	public void setHashTags(List<HashTag> hashtags) {
		this.hashTags = hashtags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int compareTo(Tweet o) {
		int ret = 1;
		
		if (o != null ) {
			if (o.getCreationDate().isEqual(this.getCreationDate())) {
				ret = 0;
			} else if (o.getCreationDate().isBefore(this.getCreationDate())) {
				ret = -1;
			}
		}
		return ret;
	}
}
