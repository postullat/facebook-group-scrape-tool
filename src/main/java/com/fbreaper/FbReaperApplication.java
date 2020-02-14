package com.fbreaper;

import com.fbreaper.schedulers.PostDataToFirebaseAggregatorTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableScheduling
@Service
public class FbReaperApplication implements CommandLineRunner{

	@Autowired
	PostDataToFirebaseAggregatorTask postDataToFirebaseAggregatorTask;

	public static void main(String[] args) { SpringApplication.run(FbReaperApplication.class, args); }

	@Override
	public void run(String... args){
		postDataToFirebaseAggregatorTask.aggregatePostData();
	}

}