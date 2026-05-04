package br.com.smartcart.application.service;

import br.com.smartcart.domain.entities.Customer;
import br.com.smartcart.domain.valueobjects.request.CustomerVO;

public interface CustomerService {
    Customer getUser(CustomerVO customer);
}

