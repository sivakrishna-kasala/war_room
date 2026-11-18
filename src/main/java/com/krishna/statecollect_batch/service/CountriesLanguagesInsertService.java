package com.krishna.statecollect_batch.service;


import com.krishna.statecollect_batch.entity.CountriesLanguages;
import com.krishna.statecollect_batch.entity.CountryLanguage;
import com.krishna.statecollect_batch.repository.jpa.CountriesLanguagesRepository;
import com.krishna.statecollect_batch.repository.jpa.CountryLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountriesLanguagesInsertService {

    @Autowired
    private CountriesLanguagesRepository countriesLanguagesRepository;

    @Autowired
    private CountryLanguageRepository countryLanguageRepository;

    public void insert() {

        List<CountryLanguage> countryList=countryLanguageRepository.findAll();

        if (!countryList.isEmpty()){
            List<CountriesLanguages> languages=countryList.stream().map(each->{
                CountriesLanguages la=new CountriesLanguages();
                la.setCountryCode(each.getCountryCode());
                la.setLanguage(each.getLanguage());
                la.setIsOfficial(each.getIsOfficial());
                la.setPercentage(each.getPercentage());
                return la;
            }).toList();

            countriesLanguagesRepository.saveAll(languages);
        }
    }
}
