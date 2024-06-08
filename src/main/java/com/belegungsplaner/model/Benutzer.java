package main.java.com.belegungsplaner.model;

public class Benutzer {
    private String name;
    private String email;

    public Benutzer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
