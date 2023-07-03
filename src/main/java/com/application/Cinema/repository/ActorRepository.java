package com.application.Cinema.repository;

import com.application.Cinema.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

//    @Query("select v from Actor v where v.email = ?1")
    Optional<Actor> findActorById(Integer id);
    Optional<Actor> findActorByNameAndDob(String name, LocalDate dob);
}
