package se.filiprydberg.movie.model;

public class FilmingLocation {

    private String address;
    private String trivia;

    public FilmingLocation() {
    }

    public FilmingLocation(String address, String trivia) {
        this.address = address;
        this.trivia = trivia;
    }

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
}
