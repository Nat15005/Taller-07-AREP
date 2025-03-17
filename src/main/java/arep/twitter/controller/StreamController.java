package arep.twitter.controller;

import arep.twitter.model.Post;
import arep.twitter.model.Stream;
import arep.twitter.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stream")
public class StreamController {

    @Autowired
    private StreamService streamService;

    @GetMapping
    public Stream getStream() {
        return streamService.getStreamById(1L); // Asumiendo que el stream tiene ID = 1
    }

    @GetMapping("/posts")
    public List<Post> getPostsInStream() {
        Stream stream = streamService.getStreamById(1L); // Asumiendo que el stream tiene ID = 1
        return stream.getPosts(); // Devuelve la lista de posts asociados al stream
    }
}