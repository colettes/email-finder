package edu.depaul.email;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;



class EmailFinderTest {

    // HELPER to make sure files are clear before tests.
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

    @Test
    @DisplayName("Tests EmailFinder Main with a valid link")
    void testEmailFinderMain() {
        clearFiles();
        String[] url = {"https://www.cdm.depaul.edu/"};
        EmailFinder ef = new EmailFinder();
        assertDoesNotThrow(() -> ef.main(url));
    }

    @Test
    @DisplayName("Tests EmailFinder with a valid link")
    void testEmailFinderRun() {
        clearFiles();
        String[] url = {"https://www.cdm.depaul.edu/"};
        EmailFinder ef = new EmailFinder();
        assertDoesNotThrow(() -> ef.run(url));
    }

    @Test
    @DisplayName("Tests EmailFinder with a invalid link")
    void testEmailFinderRunWithNoURL() {
        clearFiles();
        String[] url = {""};
        EmailFinder ef = new EmailFinder();
        assertDoesNotThrow(() -> ef.run(url));
    }

    @Test
    @DisplayName("Tests EmailFinder with a multiple arguments")
    void testEmailFinderRunWithMultipleArgs() {
        clearFiles();
        String[] url = new String[]{"https://www.cdm.depaul.edu/","2"};
        EmailFinder ef = new EmailFinder();
        assertDoesNotThrow(() -> ef.run(url));
    }

    @Test
    @DisplayName("Test that EmailFinder with a valid link will produce files")
    void testEmailFinderOutputFromWeb() {
        clearFiles();
        String[] url = {"https://www.cdm.depaul.edu/"};
        EmailFinder ef = new EmailFinder();
        ef.run(url);
        File fe = new File("email.txt"), fb = new File("bad-links.txt"), fg = new File("good-links.txt");
        assertTrue(fe.exists() && fb.exists() && fg.exists());
    }
}