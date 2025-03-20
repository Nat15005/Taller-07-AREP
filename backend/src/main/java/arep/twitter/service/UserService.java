package arep.twitter.service;

import arep.twitter.model.User;
import arep.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            user.setUsername(user.getEmail().split("@")[0]);
        }

        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserById(String id) { // Cambia de Long a String
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}