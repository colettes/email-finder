package edu.depaul.email;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.io.OutputStream;
import java.util.stream.Stream;


class PageFetcherTest {

    @Test
    @DisplayName("Test getString returns a document when URL is good.")
    void testGetStringValid() {
        PageFetcher pf = new PageFetcher();
        String url = "http://shopteti.com";
        assertTrue(pf.getString(url)!= null);
    }

    @ParameterizedTest
    @MethodSource("badLinkProvider")
    @DisplayName("Test getString throws an exception when URLs are bad.")
    void testGetStringInvalid(String url) {
        PageFetcher pf = new PageFetcher();
        assertThrows(EmailFinderException.class, () -> pf.getString(url));
    }

    private static Stream<Arguments> badLinkProvider() {
        return Stream.of(
                // bad link
                Arguments.of("httttp://shopteti.com"),
                // broken link
                Arguments.of("www.shoptetii.com"),
                // no link
                Arguments.of(""));
    }

    @Test
    @DisplayName("Test get method returns a document when URL is good.")
    void testGetValidFromWeb() {
        PageFetcher pf = new PageFetcher();
        String url = "http://shopteti.com";
        assertTrue(pf.get(url)!= null);
    }

    // TODO: unsure how to use fromFile in get method. invalid.
    @Test
    @DisplayName("Test get method returns a document when URL is good.")
    void testGetValidFromFile() {
        PageFetcher pf = new PageFetcher();
        String url = "www.shopteti.com";
        assertTrue(pf.get(url)!= null);
    }

    @ParameterizedTest
    @MethodSource("badLinkProvider2")
    @DisplayName("Test getString throws an exception when URLs are bad.")
    void testGetInvalid(String url) {
        PageFetcher pf = new PageFetcher();
        assertThrows(EmailFinderException.class, () -> pf.get(url));
    }

    private static Stream<Arguments> badLinkProvider2() {
        return Stream.of(
                // bad link
                Arguments.of("httttp://shopteti.com"),
                // broken link
                Arguments.of("www.shoptetii.com"),
                // no link
                Arguments.of(""));
    }

}
