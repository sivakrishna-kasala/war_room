package com.krishna.statecollect_batch.service;

import com.krishna.statecollect_batch.dao.CustomerAddressDAO;
import com.krishna.statecollect_batch.dto.CustomerAddressDTO;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CustomerAddressService {

    private final static Logger LOG= LogManager.getLogger(CustomerAddressService.class);

    @Autowired
    private CustomerAddressDAO customerAddressDAO;

    public void fetchCustomerAddress() {

        List<CustomerAddressDTO>response=customerAddressDAO.fetchCustomerAddressCity();


        List<CustomerAddressDTO> sortedResponse=response.stream()
                .sorted(
                        Comparator.comparingInt(
                                CustomerAddressDTO::getCustomerId
                        )
                ).filter(customer->customer.getActive().equals(true))
                .toList();

                //.thenComparing(CustomerAddressDTO::getCustomerName));

        List<String>responseNames=response.stream().sorted(Comparator.comparingInt(cus->cus.getCustomerName().length())).map(c->((CustomerAddressDTO) c).getCustomerName()).toList();

        LOG.info("Data is {}",responseNames);

        List<Integer> idsss=sortedResponse.stream().map(cc->Integer.valueOf(cc.getCustomerId())).toList();

        LOG.info("daaa :{} ",idsss);

        //Integer resp= customerAddressDAO.insertCopyCustomerAddress(sortedResponse);

        //LOG.info("data : {}",resp);


    }
}
