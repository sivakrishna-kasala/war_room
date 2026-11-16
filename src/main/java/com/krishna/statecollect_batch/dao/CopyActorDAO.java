package com.krishna.statecollect_batch.dao;

import com.krishna.statecollect_batch.dto.ActorDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import com.krishna.statecollect_batch.service.CopyActorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CopyActorDAO {
    private final static Logger LOG= LogManager.getLogger(CopyActorDAO.class);

    @Autowired
    private NamedParameterJdbcTemplate databaseSivaTemplate;

    public int insertActor(List<ActorDTO> responseActors) {
        String query="INSERT INTO ACTOR(ACTOR_ID,FIRST_NAME,LAST_NAME,LAST_UPDATE) VALUES(:actorId,:firstName,:lastName,:lastUpdate)";

        try{
            SqlParameterSource[] batchParams = responseActors.stream()
                    .map(BeanPropertySqlParameterSource::new)
                    .toArray(SqlParameterSource[]::new);
            int[] result = databaseSivaTemplate.batchUpdate(query, batchParams);

            return result.length;

        }catch (Exception e){
            throw new ApplicationException(500,e.getMessage(),e);
        }
    }
}
