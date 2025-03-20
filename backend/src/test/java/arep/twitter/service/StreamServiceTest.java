package arep.twitter.service;

import arep.twitter.model.Stream;
import arep.twitter.repository.StreamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StreamServiceTest {

    @Mock
    private StreamRepository streamRepository;

    @InjectMocks
    private StreamService streamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStreamSuccess() {
        Stream stream = new Stream();
        stream.setId(1L);
        when(streamRepository.save(any(Stream.class))).thenReturn(stream);

        Stream createdStream = streamService.createStream(stream);

        assertNotNull(createdStream);
        assertEquals(1L, createdStream.getId());
        verify(streamRepository, times(1)).save(stream);
    }

    @Test
    void testGetStreamByIdSuccess() {
        Stream stream = new Stream();
        stream.setId(1L);
        when(streamRepository.findById(1L)).thenReturn(Optional.of(stream));

        Stream foundStream = streamService.getStreamById(1L);

        assertNotNull(foundStream);
        assertEquals(1L, foundStream.getId());
        verify(streamRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStreamByIdNotFound() {
        when(streamRepository.findById(1L)).thenReturn(Optional.empty());

        Stream foundStream = streamService.getStreamById(1L);

        assertNull(foundStream);
        verify(streamRepository, times(1)).findById(1L);
    }
}