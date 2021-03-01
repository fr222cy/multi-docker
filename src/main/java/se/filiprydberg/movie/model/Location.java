package se.filiprydberg.movie.model;

public class Location {

    private String address;
    private String trivia;
    private String imageUrl;
    private LocationLatLng locationLatLng;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrivia() {
        return trivia;
    }

    public void setTrivia(String trivia) {
        this.trivia = trivia;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocationLatLng getLocationLatLng() {
        return locationLatLng;
    }

    public void setLocationLatLng(LocationLatLng locationLatLng) {
        this.locationLatLng = locationLatLng;
    }
}
