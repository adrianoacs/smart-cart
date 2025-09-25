package br.com.smartcart;

import br.com.smartcart.domain.entities.Customer;
import br.com.smartcart.infraestructure.repositories.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartCartApplication {

    public SmartCartApplication(CustomerRepository customerRepository){
        // creating customer for test
        customerRepository.save(Customer.builder().name("Customer 1").build());
        customerRepository.save(Customer.builder().name("Customer 2").build());
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartCartApplication.class);

    }
}
