package com.application.Cinema.service;

import com.application.Cinema.model.Actor;
import com.application.Cinema.repository.ActorRepository;
import com.application.Cinema.util.exception_handling.actorException.ActorNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> getActors() {
        return actorRepository.findAll();
    }

    public Actor getActorById(Integer id) {
        log.info("method getActorById in ActorService");
        Optional<Actor> foundActor = actorRepository.findById(id);
        return foundActor.orElseThrow(ActorNotFoundException::new);
    }

    @Transactional
    public void createActor(Actor actor) {
        log.info("method addNewActor in ActorService");
        Optional<Actor> existedActor = actorRepository.findActorByNameAndDob(actor.getName(), actor.getDob());
        if (existedActor.isPresent()) {
            throw new IllegalArgumentException("actor has already existed");
        }
        enrichActor(actor);
        actorRepository.save(actor);
    }

    @Transactional
    public void updateActor(Integer id, Actor actor) {
        log.info("method updateActor in ActorService");
        Actor existedActor = actorRepository.findActorById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "actor does not exists"
                ));
        existedActor.setName(actor.getName());
        existedActor.setDob(actor.getDob());
        existedActor.setAge(actor.getAge());
        existedActor.setUpdatedAt(LocalDateTime.now());
        actorRepository.save(existedActor);
    }

    @Transactional
    public void deleteActor(Integer id) {
        log.info("method deleteActor in ActorService");
        boolean exists = actorRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("actor does not exists");
        }
        actorRepository.deleteById(id);
    }

    private void enrichActor(@NotNull Actor actor) {
        actor.setCreatedAt(LocalDateTime.now());
        actor.setUpdatedAt(LocalDateTime.now());
    }
}
