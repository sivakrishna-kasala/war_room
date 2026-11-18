package com.krishna.statecollect_batch.repository.jpa;

import com.krishna.statecollect_batch.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Integer> {
}
