package se.filiprydberg.movie.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.filiprydberg.movie.model.FilmingLocation;
import se.filiprydberg.movie.model.Location;
import se.filiprydberg.movie.model.exception.LocationNotFoundException;
import se.filiprydberg.movie.model.Movie;
import se.filiprydberg.movie.service.BingService;
import se.filiprydberg.movie.service.ImdbService;
import se.filiprydberg.movie.service.MapBoxService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationHandler {

    @Autowired
    private MovieHandler movieHandler;

    @Autowired
    private ImdbService imdbService;

    @Autowired
    private MapBoxService mapBoxService;

    @Autowired
    private BingService bingService;

    public List<Location> getLocationsByMovie(String title) {
        Movie movie = movieHandler.getMovieByTitle(title);

        List<FilmingLocation> filmingLocations = imdbService.findFilmingLocationByImdbId(movie.getImdbId());

        if (filmingLocations.isEmpty()) {
            throw new LocationNotFoundException();
        }

        return filmingLocations.stream().map(v -> {
          Location location = new Location();
          location.setAddress(v.getAddress());
          location.setTrivia(v.getTrivia());
          location.setLocationLatLng(mapBoxService.geocodeStringToCoordinates(v.getAddress()));
          return location;
        }).collect(Collectors.toList());
    }
}
