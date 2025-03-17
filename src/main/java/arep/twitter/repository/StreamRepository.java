package arep.twitter.repository;

import arep.twitter.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamRepository extends JpaRepository<Stream, Long> {
}