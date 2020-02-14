package com.fbreaper.utils.aggregators.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@PropertySource(value = "classpath:properties/PostDataToFirebase.properties")
@Component
public class PostDataToFirebaseAggregatorConfig {

    @Value("${fb.login}")
    private String fbLogin;
    @Value("${fb.pass}")
    private String fbPass;
    @Value("${fb.group.url}")
    private String fbGroupUrl;
    @Value("${posts.to.fetch}")
    private Integer postsToFetch;

    @Override
    public String toString() {
        return "PostDataToFirebaseAggregatorConfig{" +
                ", fbLogin='" + fbLogin + '\'' +
                ", fbPass='" + fbPass + '\'' +
                ", fbGroupUrl='" + fbGroupUrl + '\'' +
                ", postsToFetch=" + postsToFetch +
                '}';
    }
}
