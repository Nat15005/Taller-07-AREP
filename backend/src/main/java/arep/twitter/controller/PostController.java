package arep.twitter.controller;

import arep.twitter.model.Post;
import arep.twitter.model.Stream;
import arep.twitter.model.User;
import arep.twitter.service.PostService;
import arep.twitter.service.UserService;
import arep.twitter.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private StreamService streamService;

    @GetMapping
    public List<Post> getPosts(){
        return postService.getAllPosts();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        User user = userService.getUserById(post.getUser().getId());
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        post.setUser(user);

        Stream stream = streamService.getStreamById(1L);
        if (stream == null) {
            throw new RuntimeException("Stream no encontrado");
        }
        post.setStream(stream);

        return postService.createPost(post);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }
}