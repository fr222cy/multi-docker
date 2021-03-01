package se.filiprydberg.movie.controller;

import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/info")
public class InfoController {


    @GetMapping("server-name")
    public String getServer() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    @GetMapping("server-time")
    public String getServerTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
