package arep.twitter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;
@Entity
public class Post {
    @Id
    private Long id;
    private String message;
    private User user;
    private Date dateStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
