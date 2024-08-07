package io.github.korean5078.myblog;

import io.github.korean5078.myblog.domain.Post;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String posts(@PageableDefault(size = 5) Pageable pageable, Model model) {
        Page<Post> pages = postService.getPostPage(pageable);
        model.addAttribute("page", pages);
        model.addAttribute("posts", pages.getContent());
        return "blog/posts";
    }

    @GetMapping("/add")
    public String createForm() {
        return "blog/addPost";
    }

    @PostMapping("/add")
    public String create(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content, Model model) {
        log.info("{}, {}", title, content);
        Post post = postService.createPost(new Post(title, content));
        model.addAttribute("post", post);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable(name = "postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        model.addAttribute("post", post);
        return "blog/post";
    }

    @GetMapping("/{postId}/edit")
    public String editPost(@PathVariable(name = "postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        model.addAttribute("post", post);
        return "blog/edit";
    }

    @PostMapping("/{postId}/edit")
    public String updatePost(@PathVariable(name = "postId") Long postId,
                             @RequestParam(name = "title") String title,
                             @RequestParam(name = "content") String content,
                             Model model) {
        Post post = postService.findOne(postId);
        post.setTitle(title);
        post.setContent(content);
        postService.update(post);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/{postId}/delete")
    public String deletePost(@PathVariable(name = "postId") Long postId) {
        postService.delete(postId);
        return "redirect:/post";
    }
}
