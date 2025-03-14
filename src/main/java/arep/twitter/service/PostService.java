package arep.twitter.service;

import arep.twitter.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private repository postRepository;

    public List<Post> getStream() {
        return repository.findAll();
    }
    public Optional<Post> getPostById(Long id) {
        return repository.findById(id);
    }

    public Post createProperty(Post post) {
        return repository.save(post);
    }


}
