package com.krishna.statecollect_batch.dao;


import com.krishna.statecollect_batch.dto.FilmRecordDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmRecordDAO {

    private final static Logger LOG= LogManager.getLogger(FilmRecordDAO.class);

    @Autowired
    private NamedParameterJdbcTemplate actorTemplate;

    @Autowired
    private NamedParameterJdbcTemplate databaseSivaTemplate;

    public List<FilmRecordDTO> fetchFilm() {

        String query="SELECT F.TITLE AS film_name,F.RELEASE_YEAR,F.RATING,L.NAME AS LANGUAGE FROM FILM F JOIN LANGUAGE L ON F.LANGUAGE_ID=L.LANGUAGE_ID";

        try{
            List<FilmRecordDTO> response=actorTemplate.query(query,new BeanPropertyRowMapper<>(FilmRecordDTO.class));

            LOG.info("response is {}",response);
            return response;

        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(400,e.getMessage(),e);
        }
    }

    public Integer insertFilms(List<FilmRecordDTO> inputData) {

        String query="INSERT INTO FILM_RECORD (FILM_NAME,RELEASE_YEAR,RATING,LANGUAGE) VALUES(:filmName,:releaseYear,:rating,:language)";

        try{

            SqlParameterSource[] batchParams = inputData.stream()
                    .map(BeanPropertySqlParameterSource::new)
                    .toArray(SqlParameterSource[]::new);

            int[]response=databaseSivaTemplate.batchUpdate(query,batchParams);
            LOG.info("data updated :{}",response);

            return response.length;
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(400,e.getMessage(),e);
        }
    }
}
