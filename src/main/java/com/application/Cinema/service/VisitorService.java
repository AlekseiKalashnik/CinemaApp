package com.application.Cinema.service;

import com.application.Cinema.model.Visitor;
import com.application.Cinema.repository.VisitorRepository;
import com.application.Cinema.util.exception_handling.visitorException.VisitorNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class VisitorService {

    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public List<Visitor> getVisitors() {
        return visitorRepository.findAll();
    }

    public Visitor getVisitorById(Integer id) {
        log.info("method getVisitorById in VisitorService");
        Optional<Visitor> visitorOptional = visitorRepository.findById(id);
        return visitorOptional.orElseThrow(VisitorNotFoundException::new);
    }

    public void addNewVisitor(Visitor visitor) {
        log.info("method addNewVisitor in VisitorService");
        Optional<Visitor> visitorOptional = visitorRepository.findVisitorByEmail(visitor.getEmail());
        if(visitorOptional.isPresent()) {
            throw new IllegalArgumentException("email taken");
        }
        enrichVisitor(visitor);
        visitorRepository.save(visitor);
    }

    public void deleteVisitor(Integer visitorId) {
        log.info("method deleteVisitor in VisitorService");
        boolean exists = visitorRepository.existsById(visitorId);
        if(!exists) {
            throw new IllegalArgumentException("visitor with id " +
                    visitorId + " does not exists");
        }
        visitorRepository.deleteById(visitorId);
    }

    @Transactional
    public void updateVisitor(Integer visitorId, String name, String email) {
        log.info("method updateVisitor in VisitorService");
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new IllegalStateException(
                        "visitor with id " + visitorId + " does not exists"
                ));

        if(name != null && name.length() > 0 && !Objects.equals(visitor.getName(), name)) {
            visitor.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(visitor.getEmail(), email)) {
            Optional<Visitor> visitorOptional = visitorRepository
                    .findVisitorByEmail(email);
            if(visitorOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            visitor.setEmail(email);
        }
        visitor.setUpdatedAt(LocalDateTime.now());
    }

    private void enrichVisitor(Visitor visitor) {
        visitor.setCreatedAt(LocalDateTime.now());
        visitor.setUpdatedAt(LocalDateTime.now());
        visitor.setCreatedWho("ADMIN");
    }
}
