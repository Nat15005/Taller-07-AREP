package arep.twitter.controller;

import arep.twitter.model.Post;
import arep.twitter.model.Stream;
import arep.twitter.model.User;
import arep.twitter.service.FirebaseService;
import arep.twitter.service.PostService;
import arep.twitter.service.UserService;
import arep.twitter.service.StreamService;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private FirebaseService firebaseService;

    @GetMapping
    public List<Post> getPosts(){
        return postService.getAllPosts();
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = firebaseService.verifyToken(idToken);

            String email = decodedToken.getEmail();

            User user = userService.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado en la base de datos");
            }
            post.setUser(user);

            Stream stream = streamService.getStreamById(1L);
            if (stream == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stream no encontrado");
            }
            post.setStream(stream);

            Post createdPost = postService.createPost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido");
        }
    }


    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }
}