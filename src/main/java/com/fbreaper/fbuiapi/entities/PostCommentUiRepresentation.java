package com.fbreaper.fbuiapi.entities;

import com.codeborne.selenide.SelenideElement;
import java.util.ArrayList;
import java.util.List;

public class PostCommentUiRepresentation extends AbstractUiEntity {

    private SelenideElement rawComment;
    private static final String POST_COMMENT_AUTHOR = ".//a[contains(@class,'Actor')]";
    private String postCommentAuthor;

    public PostCommentUiRepresentation(SelenideElement comment) {
        this.rawComment = comment;
    }

    public String fetchPostCommentAuthor(){
        postCommentAuthor = fetchInnerTextData(rawComment.$x(POST_COMMENT_AUTHOR));
        return postCommentAuthor;
    }

    public String fetchPostCommentBody(){
        if (postCommentAuthor != null){
            return rawComment.getText().replace(postCommentAuthor, "");
        } else {
            fetchPostCommentAuthor();
            return rawComment.getText().replace(postCommentAuthor, "");
        }
    }

    public static class Builder{
        private List<PostCommentUiRepresentation> comments;
        private List<SelenideElement> rawComments;

        public Builder(List<SelenideElement> rawComments){
            this.rawComments = rawComments;
        }

        public List<PostCommentUiRepresentation> build(){
            this.comments = new ArrayList<>();
            rawComments.stream().forEach(e -> comments.add(new PostCommentUiRepresentation(e)));
            return comments;
        }
    }
}
