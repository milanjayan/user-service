package com.ecommerce.userservice.repositories;

import com.ecommerce.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndIsDeleted(String value, boolean deleted);
    Optional<Token> findByValueAndIsDeletedAndExpiryDateGreaterThan(String value, boolean deleted, Date currentDate);
}
