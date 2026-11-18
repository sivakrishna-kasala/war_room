package com.krishna.statecollect_batch.service;


import com.krishna.statecollect_batch.entity.Country;
import com.krishna.statecollect_batch.repository.jpa.CountryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryJpaService {

    private static final Logger LOG= LogManager.getLogger(CountryJpaService.class);

    @Autowired
    private CountryRepository countryRepository;

    public void country(String[] args) {

        List<Country>countryList=countryRepository.findAll();
        LOG.info("Country list :{}",countryList);
    }
}
