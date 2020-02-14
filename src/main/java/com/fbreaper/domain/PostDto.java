package com.fbreaper.domain;

import com.google.cloud.firestore.annotation.Exclude;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "group_posts")
public class PostDto implements Comparable<PostDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "post_author")
    private String postAuthor;

    @Column(name = "post_author_url", columnDefinition="TEXT")
    private String postAuthorUrl;

    @Column(name = "post_last_update")
    private Long postLastUpdate;

    @Column(name = "post_time_stamp")
    private Long postTimeStamp;

    @Column(name = "post_text", columnDefinition="TEXT")
    private String postText;

    //skip
    private String postType;

    @Column(name = "post_link", columnDefinition="TEXT")
    private String postLink;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostImage> images = new ArrayList<>();

    public static final String ID_FIELD = "id";
    public static final String POST_AUTHOR_FIELD = "postAuthor";
    public static final String POST_AUTHOR_URL_FIELD = "postAuthorUrl";
    public static final String POST_LAST_UPDATE_FIELD = "postLastUpdate";
    public static final String POST_TIME_STAMP_FIELD = "postTimeStamp";
    public static final String POST_TEXT_FIELD = "postText";
    public static final String POST_TYPE_FIELD = "postType";
    public static final String POST_LINK_FIELD = "postLink";
    public static final String IMAGES_FIELD = "images";


    @Override
    public String toString() {
        return  "PostEntity:\n" +
                "              ID = '" + id + "\n" +
                "     POST AUTHOR = '" + postAuthor + "'\n" +
                " POST AUTHOR URL = '" + postAuthorUrl + "'\n" +
                "  POST TIMESTAMP = '" + postLastUpdate + "'\n" +
                "POST_LAST_UPDATE = '" + postLastUpdate + "'\n"+
                "       POST TEXT = '" + postText + "'\n"+
                "       POST TYPE = '" + postType + "'\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDto)) return false;
        PostDto that = (PostDto) o;
        return
                Objects.equals(postAuthor, that.postAuthor) &&
                Objects.equals(postAuthorUrl, that.postAuthorUrl) &&
                Objects.equals(postLastUpdate, that.postLastUpdate) &&
                Objects.equals(postTimeStamp, that.postTimeStamp) &&
                Objects.equals(postText, that.postText);
    }

    @Override
    public int compareTo(PostDto that) {
        return Comparator.comparing(PostDto::getPostAuthor)
                .thenComparing(PostDto::getPostTimeStamp)
                .compare(this, that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postAuthor, postAuthorUrl, postLastUpdate, postTimeStamp, postText);
    }

    public Map textFieldsToHasMap(){
        return new HashMap(){{
            put(POST_AUTHOR_FIELD, postAuthor);
            put(POST_AUTHOR_URL_FIELD, postAuthorUrl);
            put(POST_LAST_UPDATE_FIELD, postLastUpdate);
            put(POST_TIME_STAMP_FIELD, postTimeStamp);
            put(POST_TEXT_FIELD, postText);
            put(POST_TYPE_FIELD, postType);
            put(POST_LINK_FIELD, postLink);
        }};
    }

    public void setPostTimeStampWithMls(String postTimeStamp) {
        this.postTimeStamp = Long.valueOf(postTimeStamp + "000");
    }

//    /*Timstamp formated similar to facebook UI. Needs to compare.*/
//    public String getOriginPostTimeStamp(){
//        try {
//            return postTimeStamp.substring(0, 9);
//        } catch (StringIndexOutOfBoundsException e){
//            log.warn("Can't get original time stamp in POST [id= '" + this.id + "', author = '" + postAuthor + "']. Possible data duplication!");
//            return "0000000000";
//        }
//    }
}
