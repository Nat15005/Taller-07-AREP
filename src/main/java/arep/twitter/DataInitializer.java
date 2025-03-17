package arep.twitter;

import arep.twitter.model.Stream;
import arep.twitter.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StreamRepository streamRepository;

    @Override
    public void run(String... args) throws Exception {
        if (streamRepository.count() == 0) {
            Stream stream = new Stream();
            streamRepository.save(stream);
        }
    }
}
