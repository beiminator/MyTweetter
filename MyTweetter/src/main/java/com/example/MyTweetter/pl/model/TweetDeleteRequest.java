package com.example.MyTweetter.pl.model;

public class TweetDeleteRequest {
	private Long idUser;
	private Long idTweet;

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdTweet() {
		return idTweet;
	}

	public void setIdTweet(Long idTweet) {
		this.idTweet = idTweet;
	}
}
