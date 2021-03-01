package se.filiprydberg.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.filiprydberg.movie.handler.LocationHandler;
import se.filiprydberg.movie.handler.MovieHandler;
import se.filiprydberg.movie.handler.PosterHandler;
import se.filiprydberg.movie.model.Location;
import se.filiprydberg.movie.model.Movie;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieHandler movieHandler;

    @Autowired
    private LocationHandler locationHandler;

    @Autowired
    private PosterHandler posterHandler;

    @GetMapping("{title}")
    public Movie movieByTitle(@PathVariable String title) {
        return movieHandler.getMovieByTitle(title);
    }

    @GetMapping("{title}/filming-locations")
    public List<Location> getFilmingLocations(@PathVariable String title) {
        return locationHandler.getLocationsByMovie(title);
    }

    @GetMapping("{title}/poster")
    public String getPosterUrl(@PathVariable String title) {
        return posterHandler.getPosterByTitle(title);
    }
}
