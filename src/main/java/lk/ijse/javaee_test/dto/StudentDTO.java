package lk.ijse.javaee_test.dto;

import java.io.Serializable;

public class StudentDTO implements Serializable {

    private int Id;
    private String name;
    private String city;
    private String email;
    private int level;

    @Override
    public String toString() {
        return "StudentDTO{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public StudentDTO(int id, String name, String city, String email, int level) {
        Id = id;
        this.name = name;
        this.city = city;
        this.email = email;
        this.level = level;
    }

    public StudentDTO() {
    }
}
