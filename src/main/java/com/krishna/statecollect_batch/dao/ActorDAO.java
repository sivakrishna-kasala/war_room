package com.krishna.statecollect_batch.dao;

import com.krishna.statecollect_batch.dto.ActorDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ActorDAO {

    private final static Logger LOG= LogManager.getLogger(ActorDAO.class);

    @Autowired
    private NamedParameterJdbcTemplate actorTemplate;


    public List<ActorDTO> getActorList(Integer limit) {

        String query="SELECT * FROM sakila.actor WHERE first_name is not null and last_name is not null LIMIT :limit";
        MapSqlParameterSource source=new MapSqlParameterSource();
        source.addValue("limit",limit);
        try {
            List<ActorDTO>response=actorTemplate.query(query,source,new BeanPropertyRowMapper<>(ActorDTO.class));
            if (response.isEmpty()){
                return Collections.emptyList();
            }
            return response;
        } catch (Exception e) {
            throw new ApplicationException(500,e.getMessage(),e);
        }
    }
}
