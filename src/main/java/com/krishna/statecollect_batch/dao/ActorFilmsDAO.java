package com.krishna.statecollect_batch.dao;


import com.krishna.statecollect_batch.dto.FilmCountDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorFilmsDAO {

    private final static Logger LOG= LogManager.getLogger(ActorFilmsDAO.class);


    @Autowired
    private NamedParameterJdbcTemplate actorTemplate;

    public List<FilmCountDTO> getList(Integer limit) {

        String query="SELECT C.FIRST_NAME,C.LAST_NAME,COUNT(*) as CUT FROM (SELECT A.ACTOR_ID,A.FIRST_NAME,A.LAST_NAME,FA.FILM_ID FROM ACTOR A LEFT JOIN FILM_ACTOR FA ON A.ACTOR_ID=FA.ACTOR_ID) C JOIN FILM F ON C.FILM_ID=F.FILM_ID GROUP BY C.FIRST_NAME,C.LAST_NAME";

        try{
            List<FilmCountDTO> response=actorTemplate.query(query,new BeanPropertyRowMapper<>(FilmCountDTO.class));

            LOG.info("data is received : {}",response);
            return response;
        }catch (Exception e){
            throw e;
        }
    }
}
