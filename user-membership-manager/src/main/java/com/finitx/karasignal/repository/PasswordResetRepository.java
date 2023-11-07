package com.finitx.karasignal.repository;

import com.finitx.karasignal.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

    @Query("select p from PasswordReset p where p.token = ?1")
    Optional<PasswordReset> findByToken(String token);
}
