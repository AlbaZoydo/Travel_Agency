
package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomersRepositoryAdapter implements CustomersRepository {

    private final CustomersJPARepository jpaRepository;

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        return jpaRepository.findByPassportNumber(id).map(this::toDomain);
    }

    private CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(customer.getEnrollmentDate())
                .active(customer.getActive())
                .enrollmentDate(customer.getEnrollmentDate() != null? customer.getEnrollmentDate(): LocalDate.now())
                .active(customer.getActive() != null? customer.getActive(): true)
                .build();
    }

    private Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate(entity.getEnrollmentDate())
                .active(entity.getActive())
                .build();
    }
}
