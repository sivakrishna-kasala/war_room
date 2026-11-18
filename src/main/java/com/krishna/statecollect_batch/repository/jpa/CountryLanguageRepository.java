package com.krishna.statecollect_batch.repository.jpa;

import com.krishna.statecollect_batch.entity.CountryLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryLanguageRepository extends JpaRepository<CountryLanguage,String> {
}
