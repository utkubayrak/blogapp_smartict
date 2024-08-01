package com.utkubayrak.blogverse.business.services.impl;

import com.utkubayrak.blogverse.business.services.IPostService;
import com.utkubayrak.blogverse.business.dto.request.PostSaveRequest;
import com.utkubayrak.blogverse.business.dto.response.PostResponse;
import com.utkubayrak.blogverse.converter.PostConverter;
import com.utkubayrak.blogverse.data.entities.ECategory;
import com.utkubayrak.blogverse.data.entities.PostEntity;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import com.utkubayrak.blogverse.data.repository.PostRepository;
import com.utkubayrak.blogverse.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public PostResponse createPost(PostSaveRequest postSaveRequest, UserEntity user) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postSaveRequest.getTitle());
        postEntity.setContent(postSaveRequest.getContent());
        postEntity.setCategories(postSaveRequest.getCategories());
        postEntity.setUser(user);
        user.getPosts().add(postEntity);
        postRepository.save(postEntity);
        return PostConverter.toResponse(postEntity);

        /*
        // PostDto'dan PostEntity'ye dönüşümü yap
        PostEntity postEntity = blogMapper.postDtoToEntity(postDto);
        // PostEntity'ye ilgili kullanıcıyı atama
        postEntity.setUser(user);
        // Kategorileri PostEntity'ye ekleme
        Set<ECategory> existingCategories = new HashSet<>(postDto.getCategories());
        postEntity.setCategories(existingCategories);
        // Kullanıcıya ait postları güncellemek
        user.getPosts().add(postEntity);
        // Kullanıcıyı kaydetme işlemi (eğer gerekliyse)
        // userRepository.save(user); // Kullanıcıyı zaten kaydetmiş olduğunuzu varsayıyorum
        // Post'u kaydetme işlemi
        postRepository.save(postEntity);
        // Son olarak, PostEntity'yi PostDto'ya dönüştürüp geri döndürme
        return blogMapper.postEntityToDto(postEntity);

         */
    }

    @Override
    public PostResponse updatePost(Long postId, PostSaveRequest postSaveRequest) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);

        PostEntity postEntity = postEntityOptional.get();

        postEntity.setTitle(postSaveRequest.getTitle());
        postEntity.setContent(postSaveRequest.getContent());
        postEntity.setCategories(postSaveRequest.getCategories());
        postRepository.save(postEntity);
        return PostConverter.toResponse(postEntity);
        /*
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
        PostEntity postEntity = postEntityOptional.get();

        PostEntity updatedPostEntity = blogMapper.postDtoToEntity(postDtoReq);
        postEntity.setTitle(updatedPostEntity.getTitle());
        postEntity.setContent(updatedPostEntity.getContent());
        postEntity.setCategories(new HashSet<>(postDtoReq.getCategories()));

        postRepository.save(postEntity);

        return blogMapper.postEntityToDto(postEntity);

         */
    }
    @Override
    public PostResponse findPostById(Long id) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(id);
        PostEntity postEntity = postEntityOptional.get();

        return PostConverter.toResponse(postEntity);
    }
    @Override
    public void deletePost(Long postId) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
        PostEntity postEntity = postEntityOptional.get();
        UserEntity userEntity = postEntity.getUser();
        userEntity.getPosts().remove(postEntity);
        postEntity.setUser(null);
        postRepository.save(postEntity);


        postRepository.delete(postEntity);
    }

    @Override
    public List<PostResponse> getAllPost() {
        List<PostEntity> postEntities = postRepository.findAll();
        List<PostResponse> postResponses = PostConverter.toResponseList(postEntities);
        return postResponses;
    }

    @Override
    public List<PostResponse> getPostByCategory(ECategory category) {
        List<PostEntity> postEntities = postRepository.findByCategoriesContaining(category);
        List<PostResponse> postResponses = PostConverter.toResponseList(postEntities);
        return postResponses;
    }



    @Override
    public List<PostResponse> getPostByUserId(Long userId) {
        List<PostEntity> postEntities = postRepository.findByUserId(userId);
        List<PostResponse> postResponses = PostConverter.toResponseList(postEntities);
        return postResponses;
    }


}
