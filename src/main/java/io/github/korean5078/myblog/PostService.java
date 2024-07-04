package io.github.korean5078.myblog;

import io.github.korean5078.myblog.domain.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();

        return new ArrayList<>(posts);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post findOne(Long postId) {
        return postRepository.findById(postId).get();
    }

    public Post update(Post updatedPost) {
        return postRepository.save(updatedPost);
    }

    public void delete(Long postId) {
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
    }

    public Page<Post> getPostPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
