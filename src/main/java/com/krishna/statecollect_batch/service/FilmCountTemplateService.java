package com.krishna.statecollect_batch.service;


import com.krishna.statecollect_batch.dao.ActorFilmsDAO;
import com.krishna.statecollect_batch.dto.FilmCountDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmCountTemplateService {

    private final static Logger LOG= LogManager.getLogger(FilmCountTemplateService.class);

    @Autowired
    private ActorFilmsDAO actorFilmsDAO;

    public void filmCount(String[] args) {

        Integer limit=0;

        List<FilmCountDTO> response=actorFilmsDAO.getList(limit);

        LOG.info("Received : {}",response);
    }
}
