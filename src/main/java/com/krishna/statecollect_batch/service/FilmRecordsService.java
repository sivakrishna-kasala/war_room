package com.krishna.statecollect_batch.service;

import com.krishna.statecollect_batch.dao.FilmRecordDAO;
import com.krishna.statecollect_batch.dto.FilmRecordDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FilmRecordsService {

    private final static Logger LOG= LogManager.getLogger(FilmRecordsService.class);

    @Autowired
    private FilmRecordDAO filmRecordDAO;


    public void fetchRecords(String[] args) {

        List<FilmRecordDTO> filmResponse=filmRecordDAO.fetchFilm();

        List<FilmRecordDTO>response=filmResponse.stream().sorted(Comparator.comparing(FilmRecordDTO::getFilmName)).toList();

        Integer resp=filmRecordDAO.insertFilms(response);


    }
}
