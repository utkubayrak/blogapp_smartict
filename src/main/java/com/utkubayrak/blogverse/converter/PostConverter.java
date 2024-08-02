package com.utkubayrak.blogverse.converter;

import com.utkubayrak.blogverse.business.dto.response.PostResponse;
import com.utkubayrak.blogverse.data.entities.PostEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostConverter {

    public static PostResponse toResponse(PostEntity postEntity){
        return PostResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .fullName(postEntity.getUser().getFullName())
                .userId(postEntity.getUser().getId())
                .categories(postEntity.getCategories())
                .createdDate(postEntity.getCreatedDate())
                .updatedDate(postEntity.getUpdatedDate())
                .comments(postEntity.getComments().stream()
                .map(CommentConverter::toResponse)
                .collect(Collectors.toList()))
                .build();
    }
    public static List<PostResponse> toResponseList(List<PostEntity> postEntities) {
        return postEntities.stream()
                .map(PostConverter::toResponse)
                .collect(Collectors.toList());
    }
    }



