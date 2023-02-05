package com.application.Cinema.repository;

import com.application.Cinema.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

    @Query("select v from Visitor v where v.email = ?1")
    Optional<Visitor> findVisitorByEmail(String email);
}
