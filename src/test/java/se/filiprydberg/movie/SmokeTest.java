package se.filiprydberg.movie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.filiprydberg.movie.controller.AuthenticationController;
import se.filiprydberg.movie.controller.InfoController;
import se.filiprydberg.movie.controller.MovieController;
import se.filiprydberg.movie.controller.ViewController;
import se.filiprydberg.movie.importer.Importer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private MovieController movieController;

    @Autowired
    private ViewController viewController;

    @Autowired
    private InfoController infoController;

    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    protected Importer importer;

    @Test
    public void contextLoads() throws Exception {
        assertThat(movieController).isNotNull();
        assertThat(viewController).isNotNull();
        assertThat(infoController).isNotNull();
        assertThat(authenticationController).isNotNull();
    }
}
