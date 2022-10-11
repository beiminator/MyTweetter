package com.example.MyTweetter.pl.model;

import com.example.MyTweetter.data.model.Tweet;

public class TweetRequest {
	private Long idUser;
	private String text;
	
	public boolean isValid() {
		boolean valid = true;
		if (idUser == null || idUser == 0) {
			valid = false;
		}
		if (text == null || text.length() > Tweet.TEXT_LENGTH) {
			valid = false;
		}
		return valid;
	}
	
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
