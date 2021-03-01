package se.filiprydberg.movie.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.filiprydberg.movie.model.Movie;
import se.filiprydberg.movie.model.exception.PosterNotFoundException;
import se.filiprydberg.movie.service.BingService;

@Component
public class PosterHandler {

    @Autowired
    private MovieHandler movieHandler;

    @Autowired
    private BingService bingService;

    public String getPosterByTitle(String title) {
        Movie movie = movieHandler.getMovieByTitle(title);
        String imageUrl = bingService.getImageUrlForMovie(movie.getPrimaryTitle(), movie.getYear());
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new PosterNotFoundException();
        }
        return imageUrl;
    }
}
