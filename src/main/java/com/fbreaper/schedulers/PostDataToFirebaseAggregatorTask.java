package com.fbreaper.schedulers;

import com.fbreaper.service.PostDataToStorageAggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostDataToFirebaseAggregatorTask {

    @Autowired
    PostDataToStorageAggregatorService postDataToFirebaseAggregator;

    @Value("${scheduling.enabled}")
    private Boolean isSchedulingEnabled;

    private Logger log = LoggerFactory.getLogger(PostDataToFirebaseAggregatorTask.class);

    public void aggregatePostData(){
        if (isSchedulingEnabled) return;
        postDataToFirebaseAggregator.aggregatePostData();
    }

}
