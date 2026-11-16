package com.krishna.statecollect_batch.util.comparator.constant;

import com.krishna.statecollect_batch.dto.CustomerAddressDTO;

import java.util.Comparator;

public class CustomerAddressComparator implements Comparator<CustomerAddressDTO> {
    @Override
    public int compare(CustomerAddressDTO o1, CustomerAddressDTO o2) {
        return o1.getCustomerName().compareTo(o2.getCustomerName());
    }
}
