package se.filiprydberg.movie.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.filiprydberg.movie.model.Movie;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByPrimaryTitleLike(String primaryTitle);
}
