package com.krishna.statecollect_batch.repository.jpa;

import com.krishna.statecollect_batch.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,String> {
}
