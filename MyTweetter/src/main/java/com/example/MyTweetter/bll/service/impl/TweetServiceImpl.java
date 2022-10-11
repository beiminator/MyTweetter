package com.example.MyTweetter.bll.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.MyTweetter.bll.service.TweetService;
import com.example.MyTweetter.data.model.HashTag;
import com.example.MyTweetter.data.model.Tweet;
import com.example.MyTweetter.data.model.User;
import com.example.MyTweetter.data.repository.HashTagRepository;
import com.example.MyTweetter.data.repository.TweetRepository;
import com.example.MyTweetter.data.repository.UserRepository;
import com.example.MyTweetter.pl.exeption.UserNotFoundException;

@Service

public class TweetServiceImpl implements TweetService {

	private final UserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final HashTagRepository hashTagRepository;
	
	public TweetServiceImpl(
			UserRepository userRepository,
			TweetRepository tweetRepository,
			HashTagRepository hashTagRepository) {
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
		this.hashTagRepository = hashTagRepository;
	}
	
	@Override
	public void saveTweet(Long idUser, String text) throws UserNotFoundException {
		
		User user = userRepository.findById(idUser).orElse(null);
		if (user == null) {
			throw new UserNotFoundException();
		}
		
		List<HashTag> hashTags = parseHashTags(text);
		Tweet tweet = new Tweet(text);
		tweet.setUser(user);
		tweet.setHashTags(hashTags);
		tweetRepository.save(tweet);
	}
	
	private List<HashTag> parseHashTags (String text) {
		
		List<HashTag> hashTags = new ArrayList<HashTag>();
		String sHashTag = null;
		HashTag hashTag = null;
		Pattern pattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find())
		{
			for (int i = 0; i < matcher.groupCount(); i++) {
				sHashTag = matcher.group(i).trim();
				hashTag = hashTagRepository.findByHashTag(sHashTag);
				if (hashTag == null)  {
					hashTag = new HashTag(sHashTag);
					hashTag = hashTagRepository.save(hashTag);
				}
				hashTags.add(hashTag);
			}
		    System.out.println(matcher.group(1));
		}
		return hashTags;
	}

	@Override
	public List<Tweet> getByHashtaags(List<String> hashtags) {
		List<Tweet> list = new ArrayList<Tweet>();
		List<String> trimmedHashtags = new ArrayList<String>();
		for (String string : hashtags) {
			trimmedHashtags.add(string.trim());
		}
		List<HashTag> hashTagList = hashTagRepository.findByHashTagIn(trimmedHashtags);

		for (HashTag hashTag : hashTagList) {
			for (Tweet tweet : hashTag.getTweets()) {
				if (!list.contains(tweet)) {
					list.add(tweet);
				}
			}
		}
		Collections.sort(list);
		return list;
	}
	
	@Override
	public List<Tweet> getAll() {
		return tweetRepository.findAll(Sort.by(Sort.Direction.ASC, "creationDate"));
	}
	
	@Override
	public void deleteTweet (Long idTweet, Long idUser) throws UserNotFoundException {
		User user = userRepository.findById(idUser).orElse(null);
		if (user == null) {
			throw new UserNotFoundException();
		}
		tweetRepository.deleteByUserAndIdTweet(user, idTweet);
	}
}
