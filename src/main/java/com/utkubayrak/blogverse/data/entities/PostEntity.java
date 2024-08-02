package com.utkubayrak.blogverse.data.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    @ElementCollection(targetClass = ECategory.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "post_category", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "category")
    private Set<ECategory> categories;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany
    private List<CommentEntity> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
