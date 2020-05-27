package edu.depaul.email;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriterTest {

    @Test
    @DisplayName("Tests writeList with given object")
    void testListWriterConstructor() throws IOException {
        String expected = "a" + "\n" + "b" + "\n" + "c" + "\n" + "d" + "\n";
        Collection<String> data = Arrays.asList(new String[]{"a", "b", "c", "d"});

        OutputStream stream = new ByteArrayOutputStream();
        ListWriter l = new ListWriter(stream);
        l.writeList(data);

        assertEquals(expected, stream.toString());
    }

    // TODO: more tests? or maybe parameterize it

}
