package com.innova.service;

import com.innova.model.Attempt;
import com.innova.repository.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttemptServiceImpl implements AttemptService{

    @Autowired
    AttemptRepository attemptRepository;

    @Override
    public Optional<Attempt> getAttemptById(String remoteAddr) {
        return attemptRepository.findById(remoteAddr);
    }

    @Override
    public Attempt saveAttempt(Attempt attempt) {
        return attemptRepository.save(attempt);
    }

    @Override
    public boolean existsByIp(String remoteAddr) {
        return attemptRepository.existsByIp(remoteAddr);
    }
}
