package se.filiprydberg.movie.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SimpleUserDetailsService implements UserDetailsService {

    @Autowired
    private Environment environment;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(environment.getProperty("movie-app.admin.username"),
                        environment.getProperty("movie-app.admin.password"),
                        Collections.emptyList());
    }

}
