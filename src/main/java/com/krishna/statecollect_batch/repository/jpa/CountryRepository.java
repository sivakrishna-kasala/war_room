package com.krishna.statecollect_batch.repository.jpa;

import com.krishna.statecollect_batch.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,String> {

}
