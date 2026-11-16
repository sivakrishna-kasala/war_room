package com.krishna.statecollect_batch.service;

import com.krishna.statecollect_batch.dao.CountryCityDAO;
import com.krishna.statecollect_batch.dto.CountryCityDTO;
import com.krishna.statecollect_batch.dto.LanguageCityDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CountryCityService {


    private final static Logger LOG= LogManager.getLogger(CountryCityService.class);


    @Autowired
    private CountryCityDAO countryCityDAO;

    public void country(String[] args) {

        List<CountryCityDTO> dbResponse=countryCityDAO.fetchCountry();

        List<LanguageCityDTO>languageCityDTOS=dbResponse.stream().filter(Objects::nonNull).sorted(Comparator.comparing(countryCityDTO -> countryCityDTO.getCountryName()))
                .map(country->{
            LanguageCityDTO dto=new LanguageCityDTO();
            dto.setLanguage(country.getLanguage());
            dto.setIsOfficial(country.getIsOfficial());
            dto.setCityPercentage(country.getCityPercentage());
            dto.setCountryCode(country.getCountryCode());
            dto.setCountryName(country.getCountryName());
            dto.setContinent(country.getContinent());
            dto.setPopulation(country.getPopulation());
            dto.setCityPopulation(country.getCityPopulation());
            dto.setCityName(country.getCityName());
            dto.setDistrict(country.getDistrict());
            dto.setCityId(country.getCityId());
            return dto;
        }).toList();

       // LOG.info("Data received :{}",languageCityDTOS);

        Map<String,List<CountryCityDTO>> mappedLanguages=dbResponse.stream().collect(Collectors.groupingBy(CountryCityDTO::getCountryName));

        Map<String,Long> mappedCountryCount=dbResponse.stream().collect(Collectors.groupingBy(CountryCityDTO::getCountryName,Collectors.counting()));

        LOG.info("Based on Country : {}",mappedCountryCount);

        Map<String,List<CountryCityDTO>> mappedCountry=dbResponse.stream().collect(Collectors.groupingBy(CountryCityDTO::getLanguage));
        Map<String,Long>mappedLanguageCount=dbResponse.stream().collect(Collectors.groupingBy(CountryCityDTO::getLanguage,Collectors.counting()));
        LOG.info("Based on Language :{}",mappedLanguageCount);

        Map<String,Long> mappedGovernmentCount=dbResponse.stream().collect(Collectors.groupingBy(CountryCityDTO::getGovernmentForm,Collectors.counting()));

        LOG.info("Based on Government : {}",mappedGovernmentCount);

        Map<String,Long> mappedCountryName=dbResponse.stream().collect(Collectors.groupingBy(CountryCityDTO::getCountryName,Collectors.counting()));

        LOG.info("Based on countryName : {}",mappedCountryName);

        Map<String,Integer> mappedCountryPopulation=dbResponse.stream().collect(Collectors.groupingBy(CountryCityDTO::getCountryName,Collectors.summingInt(CountryCityDTO::getCityPopulation)));

        LOG.info("Based on Population : {}",mappedCountryPopulation);

        


    }
}
