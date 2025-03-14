package arep.twitter.service;

import arep.twitter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    @Autowired
    private repository UserRepository;

    public List<User> getStream() {
        return repository.findAll();
    }
    public Optional<User> getPostById(Long id) {
        return repository.findById(id);
    }

    public User createProperty(User user) {
        return repository.save(user);
    }
}
