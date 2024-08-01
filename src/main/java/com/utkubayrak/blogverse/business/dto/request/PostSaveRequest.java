package com.utkubayrak.blogverse.business.dto.request;

import com.utkubayrak.blogverse.data.entities.ECategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSaveRequest {
    private String title;
    private String content;
    private Set<ECategory> categories;
}
