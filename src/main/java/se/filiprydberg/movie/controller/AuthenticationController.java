package se.filiprydberg.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.filiprydberg.movie.model.AuthenticationRequest;
import se.filiprydberg.movie.model.AuthenticationResponse;
import se.filiprydberg.movie.conf.SimpleUserDetailsService;
import se.filiprydberg.movie.util.JwtUtil;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SimpleUserDetailsService simpleUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }
        final String jwt = jwtUtil.generateToken(simpleUserDetailsService.loadUserByUsername(request.getUsername()));
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
