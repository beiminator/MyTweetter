package com.example.MyTweetter.bll.service;

import java.util.List;

import com.example.MyTweetter.data.model.Tweet;
import com.example.MyTweetter.pl.exeption.UserNotFoundException;

public interface TweetService {

	public void saveTweet(Long idUser, String text) throws UserNotFoundException;
	
	public List<Tweet> getByHashtaags(List<String> hashtags);

	public List<Tweet> getAll();

	public void deleteTweet(Long idTweet, Long idUser) throws UserNotFoundException;
	
}
