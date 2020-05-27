package edu.depaul.email;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.jsoup.nodes.Document;


class PageParserTest {

    @Test
    @DisplayName("Unit test when no links are in doc")
    void testZeroLinks() {
        String html = "<html><div>'no link here, sorry'</div></body></html>";
        Document doc = Jsoup.parse(html);
        PageParser parser = new PageParser();
        Set<String> links = parser.findLinks(doc);

        Set<String> linkTest = new HashSet<>();
        assertEquals(linkTest, Collections.emptySet());
    }

    @ParameterizedTest
    @MethodSource("linksProvider")
    @DisplayName("Parameterized test on findLinks")
    public void testFindLinks(String html, String[] expected) {
        Document doc = Jsoup.parse(html);
        PageParser parser = new PageParser();
        Set<String> links = parser.findLinks(doc);

        Set<String> linkTest = new HashSet<>(Arrays.asList(expected));
        assertEquals(linkTest, links);
    }

    private static Stream<Arguments> linksProvider() {
        return Stream.of(
                // one link provided - repeat of testFindLinks1
                Arguments.of("<html><a href='/some/other/file.html'>my link</a></body></html>",
                        new String[]{"/some/other/file.html"}),
                // 2 links provided
                Arguments.of("<html><a href='/hello/there.html'>my link</a><a href='/bye/guy.html'>your link</a></body></html>",
                        new String[]{"/hello/there.html", "/bye/guy.html"}),
                // 3 links provided
                Arguments.of("<html><a href='/one.html'>link 1</a><a href='/two.html'>link 2</a><a href='/three.html'>link 3</a></body></html>",
                        new String[]{"/one.html", "/two.html","/three.html"}),
                // absolute link
                Arguments.of("<html><a href='https://www.mypage.com/page.html'>my link</a></body></html>",
                        new String[]{"https://www.mypage.com/page.html"}),
                // relative link
                Arguments.of("<html><a href='page.html'>my link</a></body></html>",
                        new String[]{"page.html"})
        );
    }

}