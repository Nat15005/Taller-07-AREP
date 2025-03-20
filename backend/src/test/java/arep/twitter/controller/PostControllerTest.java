package arep.twitter.controller;

import arep.twitter.model.Post;
import arep.twitter.model.Stream;
import arep.twitter.model.User;
import arep.twitter.service.FirebaseService;
import arep.twitter.service.PostService;
import arep.twitter.service.StreamService;
import arep.twitter.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private StreamService streamService;

    @Mock
    private FirebaseService firebaseService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePostSuccess() throws Exception {
        Post post = new Post();
        post.setContent("Test content");
        User user = new User();
        user.setId("1");
        post.setUser(user);

        FirebaseToken decodedToken = mock(FirebaseToken.class);
        when(firebaseService.verifyToken(anyString())).thenReturn(decodedToken);
        when(userService.getUserById("1")).thenReturn(user);
        when(streamService.getStreamById(1L)).thenReturn(new Stream());
        when(postService.createPost(any(Post.class))).thenReturn(post);

        ResponseEntity<?> response = postController.createPost(post, "Bearer validToken");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @Test
    void testCreatePostInvalidToken() throws Exception {
        when(firebaseService.verifyToken(anyString())).thenThrow(new Exception("Invalid token"));

        ResponseEntity<?> response = postController.createPost(new Post(), "Bearer invalidToken");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token no válido", response.getBody());
    }
}