package com.fbreaper.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "group_posts_images")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    //TODO: it will work slowly. Need migration to S3 backet or DMS
    @Column(name = "image_bytes")
    private byte[] imageBytes;

    public PostImage(){
    }

    public PostImage(byte[] image){
        this.imageBytes = image;
    }

}
