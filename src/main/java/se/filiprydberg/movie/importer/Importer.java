package se.filiprydberg.movie.importer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import se.filiprydberg.movie.repository.MovieRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Component
public class Importer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MovieRepository repository;

    private static final Logger log = Logger.getLogger(Importer.class.getName());

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (repository.count() == 0) {
            executorService.submit(new MovieImporter(repository));
        } else {
            log.info("movie import skipped");
        }
    }
}
