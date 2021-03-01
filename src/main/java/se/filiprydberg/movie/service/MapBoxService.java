package se.filiprydberg.movie.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.filiprydberg.movie.model.LocationLatLng;


@Component
public class MapBoxService {
    private static final String MAPBOX_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?access_token=%s";
    private static final String ACCESS_TOKEN = "pk.eyJ1IjoicnlkYmVyZ2ZpbGlwIiwiYSI6ImNrbGt2eWh2ZDBrd3Eyb3A1YWlhMTV6d2cifQ.JkvA0XVrJNvRmbjnevr2DQ";

    @Autowired
    private ObjectMapper mapper;

    public LocationLatLng geocodeStringToCoordinates(String address) {
        RestTemplate template = new RestTemplate();
        String url = String.format(MAPBOX_URL,address, ACCESS_TOKEN);
        try {
            ResponseEntity<String> response = template.getForEntity(url, String.class);
            JsonNode root = mapper.readTree(response.getBody());

            if(response.getStatusCode().isError() || response.getBody() == null) {
                return null;
            }

            JsonNode coordinates = root.path("features").get(0).path("geometry").get("coordinates");
            return new LocationLatLng(coordinates.get(0).asDouble(), coordinates.get(1).asDouble());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException e) {
            return null; // bad address data
        }
        return null;
    }
}
