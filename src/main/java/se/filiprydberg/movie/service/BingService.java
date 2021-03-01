package se.filiprydberg.movie.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class BingService {

    private static final String BING_URL = "http://www.bing.com/images/search?q=%s&qft=+filterui:imagesize-large+filterui:aspect-wide&FORM=R5IR3";

    @Autowired
    private ObjectMapper mapper;

    public String getImageUrlForMovie(String title, int year) {
        try {
            Document doc = Jsoup.connect(String.format(BING_URL, URLEncoder.encode(title + " " + year + " movie", StandardCharsets.UTF_8))).get();
            Element image = doc.select("a[m]").get(0);
            JsonNode imageNode =  mapper.readTree(image.attributes().get("m"));

            return imageNode.get("murl").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
