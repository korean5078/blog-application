package io.github.korean5078.myblog;

import io.github.korean5078.myblog.domain.Post;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
    public String posts(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
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
}
