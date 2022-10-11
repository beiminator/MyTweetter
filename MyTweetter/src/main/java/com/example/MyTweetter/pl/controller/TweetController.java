package com.example.MyTweetter.pl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyTweetter.bll.service.TweetService;
import com.example.MyTweetter.data.model.Tweet;
import com.example.MyTweetter.pl.exeption.UserNotFoundException;
import com.example.MyTweetter.pl.model.ResponseObject;
import com.example.MyTweetter.pl.model.TweetDeleteRequest;
import com.example.MyTweetter.pl.model.TweetRequest;

@RestController
@RequestMapping("tweet")
public class TweetController {

	private final TweetService tweetService;

	public TweetController(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	
	@PostMapping("")
	public ResponseEntity<ResponseObject> tweet(@RequestBody TweetRequest tweet) {
		List<String> errors = new ArrayList<String>();
		ResponseObject result;
		if (!tweet.isValid()) {
			errors.add("User not specified or text length too long");
			result = ResponseObject.ko(errors);
		} else {
			try {
				tweetService.saveTweet(tweet.getIdUser(), tweet.getText());
				result = ResponseObject.ok();
			} catch (UserNotFoundException e) {
				errors.add("User not found");
				result = ResponseObject.ko(errors);
			}
		}
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseObject> getAllTweets() {
		ResponseObject result = null;
		
		List<Tweet> tweets = tweetService.getAll();
		List<Tweet> newTweets = new ArrayList<Tweet>();
		for (Tweet tweet : tweets) {
			newTweets.add(tweet.clone(false, false));
		}
		result = ResponseObject.ok(newTweets);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/by_hashtag")
	public ResponseEntity<ResponseObject> getByHashTag(@RequestBody List<String> hashtags) {
		ResponseObject result = null;
		
		List<Tweet> tweets = tweetService.getByHashtaags(hashtags);
		List<Tweet> newTweets = new ArrayList<Tweet>();
		for (Tweet tweet : tweets) {
			newTweets.add(tweet.clone(false, false));
		}
		result = ResponseObject.ok(newTweets);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("")
	public ResponseEntity<ResponseObject> deleteTweet(@RequestBody TweetDeleteRequest deleteTweet) {
		ResponseObject result = null;
		List<String> errors = new ArrayList<>();
		if (deleteTweet.getIdTweet() == null || deleteTweet.getIdUser() == null) {
			errors.add("User or tweet not valid");
			result = ResponseObject.ko(errors);
		} else {
			try {
				tweetService.deleteTweet(deleteTweet.getIdTweet(), deleteTweet.getIdUser());
				result = ResponseObject.ok();
			} catch (UserNotFoundException e) {
				errors.add("User not found");
				result = ResponseObject.ko(errors);
			}
		}
		return ResponseEntity.ok(result);
	}
	
}
