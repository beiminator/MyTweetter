package com.example.MyTweetter.data.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MyTweetter.data.model.Tweet;
import com.example.MyTweetter.data.model.User;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
	@Transactional
	public void deleteByUserAndIdTweet(User user, Long idTweet);
}
