package com.innova.service;

import com.innova.model.Attempt;
import com.innova.repository.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public interface AttemptService {
    public Optional<Attempt> getAttemptById(String remoteAddr);

    public Attempt saveAttempt(Attempt attempt);

    public boolean existsByIp(String remoteAddr);
}
