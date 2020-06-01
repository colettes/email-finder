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
import java.util.stream.Stream;

import static edu.depaul.email.StorageService.StorageType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PageCrawlerTest {

    File fileOne = new File("src/test/resources/file-1.html");
    String fileOnePath = fileOne.getAbsolutePath();

    File fileTwo = new File("src/test/resources/file-2.html");
    String fileTwoPath = fileTwo.getAbsolutePath();

    //HELPER to make sure files are clear
    void clearFiles() {
        try{
            Files.deleteIfExists(Paths.get("bad-links.txt"));
            Files.deleteIfExists(Paths.get("good-links.txt"));
            Files.deleteIfExists(Paths.get("email.txt"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // HELPER to enable report funtion
    void addFiles(StorageService storage) {
        storage.addLocation(StorageType.BADLINKS, "bad-links.txt");
        storage.addLocation(StorageType.GOODLINKS, "good-links.txt");
        storage.addLocation(StorageType.EMAIL, "email.txt");
    }

    @Test
    @DisplayName("Test on PageCrawler constructor.")
    void testPageCrawlerConstructor() {
        StorageService storage = mock(StorageService.class);
        PageCrawler pc = new PageCrawler(storage);
        assertNotNull(pc);
    }

    @Test
    @DisplayName("Test crawl() to find correct number of emails in file-1.html.")
    void testCrawlEmails() {
        StorageService storage = mock(StorageService.class);
        PageCrawler pc = new PageCrawler(storage);
        pc.crawl(fileOnePath);
        assertEquals(4, pc.getEmails().size());
    }

    @Test
    @DisplayName("Test crawl() to find correct number of good links in file-1.html.")
    void testCrawlGoodLinks() {
        StorageService storage = mock(StorageService.class);
        PageCrawler pc = new PageCrawler(storage);
        pc.crawl(fileOnePath);
        assertEquals(2, pc.getGoodLinks().size());
    }

    @Test
    @DisplayName("Test crawl() to find correct number of bad links in file-1.html.")
    void testCrawlBadLinks() {
        StorageService storage = mock(StorageService.class);
        PageCrawler pc = new PageCrawler(storage);
        pc.crawl(fileOnePath);
        assertEquals(3, pc.getBadLinks().size());
    }

    // Test does not pass as emails exceed maxEmails.
    @Test
    @DisplayName("Test max email constraint.")
    void testMaxEmails() {
        StorageService storage = mock(StorageService.class);
        PageCrawler pc = new PageCrawler(storage);
        pc.crawl(fileTwoPath);
        assertEquals(50, pc.getEmails().size());
    }

    @ParameterizedTest
    @MethodSource ("reportProvider")
    @DisplayName("Test report function on file-1.html.")
    void testReport(int size, String location) {
        clearFiles();
        StorageService storage = new StorageService();
        addFiles(storage);
        File f = new File(location);
        PageCrawler pc = new PageCrawler(storage);
        pc.crawl(fileOnePath);
        pc.report();

        assertTrue(f.exists() && !(f.length() == size));
    }

    private static Stream<Arguments> reportProvider () {
        return Stream.of(
                // EMAILS
                Arguments.of(4, "email.txt"),
                // GOODLINKS
                Arguments.of(2, "good-links.txt"),
                // BADLINKS
                Arguments.of(3, "bad-links.txt")
        );
    }
}


