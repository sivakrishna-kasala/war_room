package com.krishna.statecollect_batch.dto;

import java.time.LocalDate;

public class FilmRecordDTO {

    private int filmRecordId;
    private String filmName;
    private Integer releaseYear;
    private String rating;
    private String language;

    public FilmRecordDTO() {
    }

    public int getFilmRecordId() {
        return filmRecordId;
    }

    public void setFilmRecordId(int filmRecordId) {
        this.filmRecordId = filmRecordId;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releasedYear) {
        this.releaseYear = releasedYear;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String name) {
        this.language = name;
    }


    @Override
    public String toString() {
        return "FilmRecordDTO{" +
                "filmRecordId=" + filmRecordId +
                ", filmName='" + filmName + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating='" + rating + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
