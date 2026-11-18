package com.krishna.statecollect_batch.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "CountriesLanguages")
public class CountriesLanguages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_language_id")
    private Integer countryLanguageId;

    @Column(name = "country_language_code")
    private String countryCode;

    @Column(name = "language")
    private String language;

    @Column(name = "is_official")
    private String isOfficial;

    @Column(name = "percentage")
    private Double percentage;


    public CountriesLanguages() {
    }

    public Integer getCountryLanguageId() {
        return countryLanguageId;
    }

    public void setCountryLanguageId(Integer countryLanguageId) {
        this.countryLanguageId = countryLanguageId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "CountriesLanguages{" +
                "countryLanguageId=" + countryLanguageId +
                ", countryCode='" + countryCode + '\'' +
                ", language='" + language + '\'' +
                ", isOfficial='" + isOfficial + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
