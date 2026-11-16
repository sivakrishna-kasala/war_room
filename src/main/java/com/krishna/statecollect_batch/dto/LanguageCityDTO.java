package com.krishna.statecollect_batch.dto;

public class LanguageCityDTO {

    private String language;
    private String isOfficial;
    private Double cityPercentage;
    private String countryCode;
    private String countryName;
    private String continent;
    private Integer population;
    private Integer cityPopulation;
    private String cityName;
    private String district;
    private Integer cityId;

    public LanguageCityDTO() {
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

    public Double getCityPercentage() {
        return cityPercentage;
    }

    public void setCityPercentage(Double cityPercentage) {
        this.cityPercentage = cityPercentage;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getCityPopulation() {
        return cityPopulation;
    }

    public void setCityPopulation(Integer cityPopulation) {
        this.cityPopulation = cityPopulation;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "LanguageCityDTO{" +
                "language='" + language + '\'' +
                ", isOfficial='" + isOfficial + '\'' +
                ", cityPercentage=" + cityPercentage +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", continent='" + continent + '\'' +
                ", population=" + population +
                ", cityPopulation=" + cityPopulation +
                ", cityName=" + cityName +
                ", district='" + district + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
