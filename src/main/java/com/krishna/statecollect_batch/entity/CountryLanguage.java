package com.krishna.statecollect_batch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CountryLanguage")
public class CountryLanguage {

    @Id
    @Column(name = "CountryCode")
    private String countryCode;

    @Column(name = "Language")
    private String language;

    @Column(name = "IsOfficial")
    private String isOfficial;

    @Column(name = "Percentage")
    private Double percentage;

//    @ManyToOne
//    @JoinColumn(name = "CountryCode")
//    private Country country;

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
}
