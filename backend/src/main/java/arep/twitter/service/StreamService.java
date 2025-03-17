package arep.twitter.service;

import arep.twitter.model.Stream;
import arep.twitter.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamService {

    @Autowired
    private StreamRepository streamRepository;

    public Stream createStream(Stream stream) {
        return streamRepository.save(stream);
    }

    public Stream getStreamById(Long id) {
        return streamRepository.findById(id).orElse(null);
    }
}
