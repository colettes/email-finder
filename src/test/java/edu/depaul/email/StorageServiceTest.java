package edu.depaul.email;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageServiceTest {

    @ParameterizedTest
    @MethodSource("listProvider")
    @DisplayName("Parameterized test on storeLists to ensure that file is created and written to.")
    public void testStoreList(StorageService.StorageType storage, String file, String[] links) throws IOException {
        Files.deleteIfExists(Paths.get("\\"+"file"));
        File f = new File(file);
        StorageService ss = new StorageService();
        Collection<String> l = Arrays.asList(links);

        ss.addLocation(storage, file);
        ss.storeList(storage, l);

        assertTrue(f.exists() && !(f.length() == 0));
    }

    private static Stream<Arguments> listProvider() {
        return Stream.of(
                // GOODLINKS test (3 entries)
                Arguments.of(StorageService.StorageType.GOODLINKS, "good-links.txt",
                        new String[]{"/good.html", "true.org", "www.hi.com"}),
                // BADLINKS test (2 entries)
                Arguments.of(StorageService.StorageType.BADLINKS, "bad-links.txt",
                        new String[]{"bad.html", "/hi.jkjkjk"}),
                // EMAILS test (4 entries)
                Arguments.of(StorageService.StorageType.EMAIL, "emails.txt",
                        new String[]{"great@gmail.com", "yo@yahoo.com", "lol@aol.com", "cnn@msn.com"})
        );
    }

    @Test
    @DisplayName("Ensure exception is thrown when no emails provided to storeList")
    void testStoreListError() {
        StorageService storage = new StorageService();
        assertThrows(EmailFinderException.class,
                () -> storage.storeList(StorageService.StorageType.EMAIL, null));
    }

    @Test
    @DisplayName("Ensure exception is thrown no location provided to addLocation")
    void testAddLocation() throws IOException {
        StorageService storage = new StorageService();
        Collection<String> l = Arrays.asList("great@gmail.com", "yo@yahoo.com");

        storage.addLocation(StorageService.StorageType.EMAIL, null);

        assertThrows(EmailFinderException.class,
                () -> storage.storeList(StorageService.StorageType.EMAIL, l));
    }



}
