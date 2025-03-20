package arep.twitter.controller;

import arep.twitter.controller.PostController;
import arep.twitter.model.Post;
import arep.twitter.model.Stream;
import arep.twitter.model.User;
import arep.twitter.service.PostService;
import arep.twitter.service.UserService;
import arep.twitter.service.StreamService;
import arep.twitter.service.FirebaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPosts() {
        Post post1 = new Post();
        Post post2 = new Post();
        List<Post> mockPosts = Arrays.asList(post1, post2);

        when(postService.getAllPosts()).thenReturn(mockPosts);

        List<Post> result = postController.getPosts();

        assertEquals(2, result.size());
        verify(postService, times(1)).getAllPosts();
    }

    @Test
    void testCreatePost_Success() {
        User user = new User();
        user.setId("1");

        Stream stream = new Stream();
        stream.setId(1L);

        Post post = new Post();
        post.setUser(user);

        when(userService.getUserById("1")).thenReturn(user);
        when(streamService.getStreamById(1L)).thenReturn(stream);
        when(postService.createPost(any(Post.class))).thenReturn(post);

        ResponseEntity<?> response = postController.createPost(post);

        assertEquals(201, response.getStatusCodeValue());
        verify(userService, times(1)).getUserById("1");
        verify(streamService, times(1)).getStreamById(1L);
        verify(postService, times(1)).createPost(any(Post.class));
    }

    @Test
    void testCreatePost_UserNotFound() {
        User user = new User();
        user.setId("1");

        Post post = new Post();
        post.setUser(user);

        when(userService.getUserById("1")).thenReturn(null);

        ResponseEntity<?> response = postController.createPost(post);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Usuario no encontrado", response.getBody());
        verify(userService, times(1)).getUserById("1");
        verifyNoInteractions(postService);
    }

    @Test
    void testCreatePost_StreamNotFound() {
        User user = new User();
        user.setId("1");

        Post post = new Post();
        post.setUser(user);

        when(userService.getUserById("1")).thenReturn(user);
        when(streamService.getStreamById(1L)).thenReturn(null);

        ResponseEntity<?> response = postController.createPost(post);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Stream no encontrado", response.getBody());
        verify(userService, times(1)).getUserById("1");
        verify(streamService, times(1)).getStreamById(1L);
        verifyNoInteractions(postService);
    }

    @Test
    void testGetPostById() {
        Post post = new Post();
        post.setId(1L);

        when(postService.getPostById(1L)).thenReturn(post);

        Post result = postController.getPost(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(postService, times(1)).getPostById(1L);
    }
}
