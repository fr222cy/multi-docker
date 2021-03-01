package se.filiprydberg.movie.importer;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import se.filiprydberg.movie.model.Movie;
import se.filiprydberg.movie.repository.MovieRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class MovieImporter implements Runnable {

    private static final Logger log = Logger.getLogger(MovieImporter.class.getName());

    private static final String FILE_NAME = "titles.tsv";
    private static final String ZIP_FILE_NAME = "titles.tsv.gz";
    private static final String DOWNLOAD_FOLDER = System.getProperty("user.home") + File.separator;
    private static final String TITLES_URL = "https://datasets.imdbws.com/title.basics.tsv.gz";

    private MovieRepository repository;

    public MovieImporter(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run() {

        Path zippedFile = Paths.get(DOWNLOAD_FOLDER + ZIP_FILE_NAME);
        Path unzippedFile = Paths.get(DOWNLOAD_FOLDER + FILE_NAME);
        try {
            log.info(String.format("downloading file from %s", TITLES_URL));
            InputStream inputStream = new URL(TITLES_URL).openStream();
            Files.copy(inputStream, zippedFile, StandardCopyOption.REPLACE_EXISTING);
            log.info(String.format("downloaded file completed %s", zippedFile));
            GZIPInputStream gis = new GZIPInputStream(new FileInputStream(zippedFile.toFile()));
            log.info(String.format("unzipping file %s", unzippedFile));
            Files.deleteIfExists(unzippedFile);
            Files.copy(gis, unzippedFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        log.info("starting Movie Import...");
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        TsvParser parser = new TsvParser(settings);
        File file = new File(unzippedFile.toUri());

        log.info("parsing rows...");
        List<String[]> allRows = parser.parseAll(file);

        log.info("filter non-movies...");
        List<String[]> movieRows = allRows.stream().filter(row -> row[1].equals("movie")).collect(Collectors.toList());
        log.info("movie Rows = " + movieRows.size());

        log.info("mapping movies...");
        List<Movie> movies = movieRows.stream().map(row -> {
            Movie movie = new Movie();
            movie.setImdbId(row[0]);
            movie.setPrimaryTitle(row[2]);
            movie.setOriginalTitle(row[3]);
            movie.setAdult(row[4].equals("1"));
            movie.setYear(row[5].matches("-?(0|[1-9]\\d*)") ? Integer.valueOf(row[5]) : null);
            movie.setRuntimeMinutes(row[7].matches("-?(0|[1-9]\\d*)") ? Integer.valueOf(row[7]) : null);
            movie.setGenres(row[8].split(","));
            return movie;
        }).collect(Collectors.toList());

        log.info("persisting movies...");

        try {
            repository.saveAll(movies);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("import Completed");
    }
}
