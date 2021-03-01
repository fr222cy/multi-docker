package se.filiprydberg.movie.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import se.filiprydberg.movie.model.FilmingLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ImdbService {

    private static final String IMDB_URL = "https://www.imdb.com/title/%s/locations";

    public List<FilmingLocation> findFilmingLocationByImdbId(String imdbId) {
        List<FilmingLocation> filmingLocationsList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(String.format(IMDB_URL, imdbId)).get();
            Elements filmingLocations = doc.select(".soda ");
            for (Element filmingLocation : filmingLocations) {

                Optional<Element> addressElement = filmingLocation.getAllElements().stream().filter(v -> v.tagName().equals("a")).findFirst();
                Optional<Element> triviaElement = filmingLocation.getAllElements().stream().filter(v -> v.tagName().equals("dd")).findFirst();
                if (addressElement.isPresent()) {
                    String trivia = "";
                    if (triviaElement.isPresent()) {
                        trivia = triviaElement.get().html();
                    }
                    filmingLocationsList.add(new FilmingLocation(addressElement.get().html(), trivia));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filmingLocationsList;
    }
}
