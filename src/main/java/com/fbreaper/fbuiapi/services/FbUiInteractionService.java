package com.fbreaper.fbuiapi.services;

import com.codeborne.selenide.SelenideElement;
import com.fbreaper.fbuiapi.pages.GroupPage;
import com.fbreaper.fbuiapi.pages.LoginPage;
import com.fbreaper.fbuiapi.entities.PostUiRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource(value = "classpath:application.properties")
public class FbUiInteractionService {

    private Logger log = LoggerFactory.getLogger(FbUiInteractionService.class);

    @Autowired
    LoginPage loginPage;

    @Autowired
    GroupPage groupPage;

    @Value("${selenide.timeout}")
    Integer postsBatchLoadPause;

    public FbUiInteractionService login(String login, String pass){
        loginPage.doLogIn(login, pass);
        log.info("Log into FB account");
        return this;
    }

    public Integer getPostsCount(){
        int size = groupPage.getAllPosts().size();
        log.info("Quantity of POSTs on current page - '" + size + "'");
        return size;
    }

    public FbUiInteractionService setGroupUrl(String url){
        log.info("Set FB group url to '" + url + "'");
        groupPage.setUrl(url);
        return this;
    }

    public FbUiInteractionService goToGroup(){
        groupPage.open();
        log.info("Open group");
        return this;
    }

    public List<PostUiRepresentation> getAllPosts(){
        log.info("Collect POSTs from FB ui");
        return groupPage.getAllPosts()
                .stream().map(e -> new PostUiRepresentation(e)).collect(Collectors.toList());
    }

    public List<PostUiRepresentation> getPosts(Integer postsCount){
        Integer count = postsCount + 1;
        List<SelenideElement> posts = groupPage.getAllPosts();
        if (posts.size() >= count) {
            log.info("Found '" + posts.size() + "' POSTS. For proceeding prepare '" + posts.subList(0, count).size() + "'");
            return wrapRawPosts(posts.subList(0, count));
        }
        for (int i = 0; i < 20; i++){
            posts = getPostsWithRetry();
            if (posts.size() >= count){
                log.info("Found '" + posts.size() + "' POSTS. For proceeding prepare '" + posts.subList(0, count).size() + "'");
                return wrapRawPosts(posts.subList(0, count));
            }
        }
        throw new RuntimeException("Can't get all posts [search dip = 20 iterations, can't download '" + count + "']." +
                " Current posts quantity '" + posts.size() + "'!");
    }

    private List<PostUiRepresentation> wrapRawPosts(List<SelenideElement> rawPosts){
       return rawPosts.stream().map(post -> new PostUiRepresentation(post) ).collect(Collectors.toList());
    }

    private List<SelenideElement> getPostsWithRetry(){
        for (int i = 0; i < 3; i++){
            try {
                return groupPage.getNewPostsBatch(postsBatchLoadPause);
            }catch (Error e){
                postsBatchLoadPause = postsBatchLoadPause * 2;
                log.warn("Fail to load POSTS batch! Time out for batches load will increase to '" + postsBatchLoadPause + "' and try again...");
            }
            groupPage.open();
        }
        throw new RuntimeException("Fail to load posts butches after all attempts...");
    }

}
