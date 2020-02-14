package com.fbreaper.domain;

import lombok.Data;

import java.util.Objects;

@Data
public class PostCommentDto implements Comparable<PostCommentDto>{

    public PostCommentDto() {
    }

    public PostCommentDto(PostDto post) {
        this.post = post;
    }

    private Long id;

    private PostDto post;

    private String commentAuthor;

    private String commentBody;

    @Override
    public String toString() {
        return "PostCommentEntity{" +
                "COMMENT AUTHOR = '" + commentAuthor + "'" +
                "  COMMENT BODY = '" + commentBody + "'" +
                '}';
    }

    @Override
    public int compareTo(PostCommentDto o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostCommentDto)) return false;
        PostCommentDto that = (PostCommentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(post, that.post) &&
                Objects.equals(commentAuthor, that.commentAuthor) &&
                Objects.equals(commentBody, that.commentBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post, commentAuthor, commentBody);
    }
}
