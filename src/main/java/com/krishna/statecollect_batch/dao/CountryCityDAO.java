package com.krishna.statecollect_batch.dao;

import com.krishna.statecollect_batch.dto.CountryCityDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryCityDAO {

    private final static Logger LOG= LogManager.getLogger(CountryCityDAO.class);

    @Autowired
    private NamedParameterJdbcTemplate countryTemplate;

    public List<CountryCityDTO> fetchCountry() {
        String query= """
                SELECT CL.CountryCode AS languageCountryCode,CL.Language AS language,CL.IsOfficial AS isOfficial,CL.Percentage AS cityPercentage,C.Code AS countryCode,C.Name AS countryName,C.Continent AS continent,C.Region AS countryRegion,C.SurfaceArea AS surfaceArea, C.IndepYear AS indepYear,C.Population AS population,C.LifeExpectancy AS lifeExpectancy,C.GNP AS gnp,C.GNPOld AS gnpOld, C.LocalName AS localName,C.GovernmentForm AS governmentForm,C.HeadOfState AS headOfState,  C.Capital AS capital,C.Code2 AS countryCode2,CI.ID AS cityId, CI.Name AS cityName,CI.CountryCode AS cityCountryCode, CI.District AS district, CI.Population AS cityPopulation
                 FROM country C 
                 JOIN city CI
                 ON C.Code = CI.CountryCode
                 JOIN countrylanguage CL
                 ON C.Code = CL.CountryCode""";

        try{

            List<CountryCityDTO> response=countryTemplate.query(query,new BeanPropertyRowMapper<>(CountryCityDTO.class));
            //LOG.info("Country details received :{} ",response);

            return response;
        } catch (Exception e) {
            throw new ApplicationException(400,e.getMessage());
        }
    }
}
