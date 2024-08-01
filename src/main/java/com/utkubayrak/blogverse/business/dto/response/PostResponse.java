package com.utkubayrak.blogverse.business.dto.response;

import com.utkubayrak.blogverse.data.entities.ECategory;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String fullName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Set<ECategory> categories;
}
