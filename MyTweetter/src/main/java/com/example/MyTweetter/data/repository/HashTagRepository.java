package com.example.MyTweetter.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MyTweetter.data.model.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
	public HashTag findByHashTag(String hashTag);
	public List<HashTag> findByHashTagIn(List<String> hashTag);
}
