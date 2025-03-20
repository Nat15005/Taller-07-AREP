package arep.twitter.controller;

import arep.twitter.model.Stream;
import arep.twitter.service.StreamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StreamControllerTest {

    @Mock
    private StreamService streamService;

    @InjectMocks
    private StreamController streamController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStream() {
        Stream stream = new Stream();
        when(streamService.getStreamById(1L)).thenReturn(stream);

        Stream result = streamController.getStream();

        assertEquals(stream, result);
    }
}