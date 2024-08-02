package com.utkubayrak.blogverse.converter;

import com.utkubayrak.blogverse.business.dto.response.CommentResponse;
import com.utkubayrak.blogverse.data.entities.CommentEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentConverter {

    public static CommentResponse toResponse(CommentEntity commentEntity) {

        return CommentResponse.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getCommentContent())
                .userId(commentEntity.getUser().getId())
                .userFullName(commentEntity.getUser().getFullName())
                .createdDate(commentEntity.getCreatedDate())
                .build();
    }
}
