package arep.twitter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
@Entity
public class Stream {
    @Id
    private Long id;
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
