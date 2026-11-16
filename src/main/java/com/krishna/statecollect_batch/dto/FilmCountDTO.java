package com.krishna.statecollect_batch.dto;

public class FilmCountDTO {

    private String firstName;
    private String lastName;
    private int cut;

    public FilmCountDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCut() {
        return cut;
    }

    public void setCut(int count) {
        this.cut = count;
    }

    @Override
    public String toString() {
        return "FilmCountDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", count=" + cut +
                '}';
    }
}
