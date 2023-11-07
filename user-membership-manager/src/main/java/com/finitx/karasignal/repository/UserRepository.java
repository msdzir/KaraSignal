package com.finitx.karasignal.repository;

import com.finitx.karasignal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    boolean existsByEmail(String email);

    @Query("select (count(u) > 0) from User u where u.phone = ?1")
    boolean existsByPhone(String phone);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);
}
