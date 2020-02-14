package com.fbreaper.repository;

import com.fbreaper.domain.PostDto;
import com.fbreaper.fbuiapi.entities.PostUiRepresentation;

public class DaoUtils {

    public static PostDto doMapPostUiRepresentationTexDataToDto(PostUiRepresentation uiEntity){
        PostDto dto = new PostDto();
        dto.setPostAuthor(uiEntity.fetchPostAuthor());
        dto.setPostText(uiEntity.fetchPostMessageWithNoDuplicationAndFilter());
        dto.setPostTimeStampWithMls(uiEntity.fetchPostTimestamp());
        dto.setPostAuthorUrl(uiEntity.fetchPostAuthorProfileLink());
        dto.setPostLink(uiEntity.fetchPostLink());
        //setPostType(dto);
        return dto;
    }

    private static void setPostType(PostDto dto) {
        String text = dto.getPostText();
        if(contains(text, "грн")) {
            dto.setPostType("offer");
        } else {
            if (contains(text, "відгук") || contains(text, "спасибі") ||
                    contains(text,"дяку") &&
                            (!contains(text,"всім дяку") && !contains(text,"дякую всім")
                                    && !contains(text,"дякую люди"))) {
                dto.setPostType("review");
            } else {
                dto.setPostType("request");
            }
        }
    }

    private static boolean contains(String src, String segment) {
        if(src.toLowerCase().contains(segment.toLowerCase())) {
            return true;
        } else {
            return false;
        }

    }
}
