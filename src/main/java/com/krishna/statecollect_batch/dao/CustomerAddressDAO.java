package com.krishna.statecollect_batch.dao;

import com.krishna.statecollect_batch.dto.CustomerAddressDTO;
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
public class CustomerAddressDAO {

    private final static Logger LOG= LogManager.getLogger(CustomerAddressDAO.class);

    @Autowired
    private NamedParameterJdbcTemplate databaseSivaTemplate;

    @Autowired
    private NamedParameterJdbcTemplate actorTemplate;

    public List<CustomerAddressDTO> fetchCustomerAddressCity() {

        String query="SELECT * FROM ( SELECT C.CUSTOMER_ID,CONCAT(C.FIRST_NAME,' ',C.LAST_NAME) AS CUSTOMER_NAME,C.EMAIL,C.ADDRESS_ID,C.ACTIVE," +
                "C.CREATE_DATE,A.ADDRESS,A.ADDRESS2,A.DISTRICT,A.CITY_ID,A.POSTAL_CODE,A.PHONE,A.LOCATION FROM CUSTOMER C" +
                " JOIN ADDRESS A ON C.ADDRESS_ID=A.ADDRESS_ID ) A JOIN " +
                "(SELECT CI.CITY_ID,CI.CITY,CO.COUNTRY FROM CITY CI JOIN COUNTRY CO ON CI.COUNTRY_ID=CO.COUNTRY_ID)" +
                "B ON A.CITY_ID=B.CITY_ID";

        try{
            List<CustomerAddressDTO> response=actorTemplate.query(query,new BeanPropertyRowMapper<>(CustomerAddressDTO.class));

            LOG.info("data received : {} ",response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(400,e.getMessage());
        }
    }

    public Integer insertCopyCustomerAddress(List<CustomerAddressDTO> inputData){

        String query="INSERT INTO CUSTOMER_ADDRESS(CUSTOMER_ID,CUSTOMER_NAME,EMAIL,ACTIVE,CREATE_DATE,ADDRESS_ID,ADDRESS,ADDRESS2,DISTRICT,CITY_ID,CITY,COUNTRY,POSTAL_CODE,PHONE) " +
                "VALUES(:customerId,:customerName,:email,:active,:createDate,:addressId,:address,:address2,:district,:cityId,:city,:country,:postalCode,:phone)";

        try{

            SqlParameterSource[] batchParams = inputData.stream()
                    .map(BeanPropertySqlParameterSource::new)
                    .toArray(SqlParameterSource[]::new);

            int [] response=databaseSivaTemplate.batchUpdate(query,batchParams);

            return response.length;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(400,e.getMessage());
        }
    }



}
