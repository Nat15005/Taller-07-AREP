package arep.twitter.controller;

import arep.twitter.model.Post;
import arep.twitter.model.Stream;
import arep.twitter.model.User;
import arep.twitter.service.FirebaseService;
import arep.twitter.service.PostService;
import arep.twitter.service.UserService;
import arep.twitter.service.StreamService;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private StreamService streamService;

    @Mock
    private FirebaseService firebaseService;

    private User testUser;
    private Post testPost;
    private Stream testStream;
    private FirebaseToken mockToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId("123");
        testUser.setEmail("user@example.com");

        testStream = new Stream();
        testStream.setId(1L);

        testPost = new Post();
        testPost.setId(1L);
        testPost.setContent("Test Post");
        testPost.setUser(testUser);
        testPost.setStream(testStream);

        mockToken = mock(FirebaseToken.class);
        when(mockToken.getEmail()).thenReturn("user@example.com");
    }

    @Test
    void testGetPosts() {
        List<Post> posts = Arrays.asList(testPost);
        when(postService.getAllPosts()).thenReturn(posts);

        List<Post> result = postController.getPosts();

        assertEquals(1, result.size());
        assertEquals("Test Post", result.get(0).getContent());
    }

    @Test
    void testCreatePost_Success() throws Exception {
        String validToken = "validToken";
        when(firebaseService.verifyToken(validToken)).thenReturn(mockToken);
        when(userService.getUserByEmail("user@example.com")).thenReturn(testUser);
        when(streamService.getStreamById(1L)).thenReturn(testStream);
        when(postService.createPost(any(Post.class))).thenReturn(testPost);

        ResponseEntity<?> response = postController.createPost(testPost, "Bearer " + validToken);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreatePost_UserNotFound() throws Exception {
        String validToken = "validToken";
        when(firebaseService.verifyToken(validToken)).thenReturn(mockToken);
        when(userService.getUserByEmail("user@example.com")).thenReturn(null);

        ResponseEntity<?> response = postController.createPost(testPost, "Bearer " + validToken);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuario no encontrado en la base de datos", response.getBody());
    }

    @Test
    void testCreatePost_InvalidToken() throws Exception {
        String invalidToken = "invalidToken";
        when(firebaseService.verifyToken(invalidToken)).thenThrow(new RuntimeException("Token no válido"));

        ResponseEntity<?> response = postController.createPost(testPost, "Bearer " + invalidToken);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token no válido", response.getBody());
    }

    @Test
    void testGetPostById() {
        when(postService.getPostById(1L)).thenReturn(testPost);

        Post result = postController.getPost(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Post", result.getContent());
    }
}
