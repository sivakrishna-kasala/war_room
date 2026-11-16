package com.krishna.statecollect_batch.service;


import com.krishna.statecollect_batch.dao.ActorDAO;
import com.krishna.statecollect_batch.dto.ActorDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorCollectService {

    private final static Logger LOG= LogManager.getLogger(ActorCollectService.class);

    @Autowired
    private ActorDAO actorDAO;

    @Autowired
    private ReportService reportService;
    
    private Integer limit=0;

    public void getActors(String[] args) {

        try{

            LOG.info("Hello,Batch just started");
            List<String>list=List.of(args);

            LOG.info("arguments : {}",list);

            if (list.size() < 2 ||list.size()>3) {
                throw new ApplicationException(400, "Invalid arguments", "Expected more than 2 arguments");
            }
            validateArguments(list);
            limit=Integer.parseInt(list.get(1));
            limit=limit==0?1000:limit;
            List<ActorDTO> dtos=actorDAO.getActorList(limit);
            dtos.stream().filter(act->act.getFirstName()!=null&&act.getLastName()!=null).toList();

            //after filtering the list
            LOG.info("actor size is {}",list.size());

            reportService.generateReport(dtos);

            LOG.info("Actor's list received from the database: [{}]",dtos);
            LOG.info("Hey, Batch ended");

        }catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(500, "Unexpected error occurred", e);
        }
        
    }

    private void validateArguments(List<String> args) {

        // Validate first argument
        if (!StringUtils.hasText(args.get(0))) {
            throw new ApplicationException(400, "Invalid arguments", "First argument cannot be empty");
        }

        // Validate second argument
        if (!StringUtils.hasText(args.get(1))) {
            throw new ApplicationException(400, "Invalid arguments", "Second argument cannot be empty");
        }
    }
}
