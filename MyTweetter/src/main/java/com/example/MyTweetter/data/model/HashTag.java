package com.example.MyTweetter.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "hashtag")
public class HashTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="id_hashtag")
	Long idHashTag;
	
	@Column(name = "hashtag", nullable = false, unique = true)
	String hashTag;
	
	@ManyToMany(mappedBy = "hashTags", targetEntity = Tweet.class, fetch = FetchType.LAZY)
	List<Tweet> tweets;
	
	public HashTag() {}
	public HashTag(String hashtag) {
		this.hashTag = hashtag;
	}
	
	public HashTag clone() {
		return clone(false);
	}
	
	public HashTag clone ( boolean cleanTweet ) {
		HashTag newHashTag = new HashTag();
		newHashTag.setHashTag(this.hashTag);
		newHashTag.setIdHashTag(this.idHashTag);
		if (!cleanTweet) {
			List<Tweet> newTweets = new ArrayList<Tweet>();
			for (Tweet tweet : this.tweets) {
				newTweets.add(tweet.clone(true, false));
			}
			newHashTag.setTweets(newTweets);
		}
		return newHashTag;
	}
	
	@Override
	public String toString() {
		return "HashTag [idHashTag=" + idHashTag + ", hashTag=" + hashTag + "]";
	}

	public Long getIdHashTag() {
		return idHashTag;
	}

	public void setIdHashTag(Long idHashTag) {
		this.idHashTag = idHashTag;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
}
