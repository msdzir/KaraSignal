package com.finitx.karasignal.repository;

import com.finitx.karasignal.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {

    @Query("select v from Verification v where v.token = ?1")
    Optional<Verification> findByToken(String token);
}
