package se.filiprydberg.movie.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.filiprydberg.movie.model.exception.BadTitleInputException;
import se.filiprydberg.movie.model.Movie;
import se.filiprydberg.movie.model.exception.MovieNotFoundException;
import se.filiprydberg.movie.repository.MovieRepository;

import java.util.List;

@Component
public class MovieHandler {

    @Autowired
    private MovieRepository repository;

    public Movie getMovieByTitle(String title) {
        if (title == null || "".equals(title.trim())) {
            throw new BadTitleInputException();
        }

        List<Movie> movies = repository.findByPrimaryTitleLike(title);

        if (movies == null || movies.isEmpty()) {
            throw new MovieNotFoundException();
        }

        return movies.stream()
                .filter(v -> v.getRuntimeMinutes() != null)
                .min((s1, s2) ->  s2.getRuntimeMinutes().compareTo(s1.getRuntimeMinutes())).get();
    }
}
