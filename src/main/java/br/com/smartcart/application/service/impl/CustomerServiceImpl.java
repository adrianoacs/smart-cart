package br.com.smartcart.application.service.impl;

import br.com.smartcart.application.service.CustomerService;
import br.com.smartcart.domain.entities.Customer;
import br.com.smartcart.domain.valueobjects.request.CustomerVO;
import br.com.smartcart.infraestructure.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getUser(CustomerVO customer) {
        return customerRepository.findByExternalId(customer.externalId())
                .orElseGet(() -> createNewCustomer(customer));
    }

    private Customer createNewCustomer(CustomerVO customer) {
        Customer newCustomer = Customer.builder()
                .name(customer.name())
                .externalId(customer.externalId())
                .email(customer.email())
                .build();
        return customerRepository.save(newCustomer);
    }
}

