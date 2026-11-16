package com.krishna.statecollect_batch.service;


import com.krishna.statecollect_batch.dao.ActorDAO;
import com.krishna.statecollect_batch.dao.CopyActorDAO;
import com.krishna.statecollect_batch.dto.ActorDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyActorService {

    private final static Logger LOG= LogManager.getLogger(CopyActorService.class);

    @Autowired
    private ActorDAO actorDAO;

    @Autowired
    private CopyActorDAO copyActorDAO;

    @Autowired
    private CopyActorReportService copyActorReportService;

    public void copyActor(String[] args) {


        try{
            List<String> list=List.of(args);

            int limit=Integer.parseInt(list.get(1));

            List<ActorDTO> responseActors=actorDAO.getActorList(limit);

            LOG.info("Actor list :{}",responseActors);

            int response=copyActorDAO.insertActor(responseActors);

            LOG.info("data :{}",response);

            copyActorReportService.report(responseActors);

        }catch (Exception e){
            throw new ApplicationException(400,e.getMessage());
        }



    }
}
