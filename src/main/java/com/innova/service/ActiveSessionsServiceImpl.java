package com.innova.service;

import com.innova.model.ActiveSessions;
import com.innova.repository.ActiveSessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveSessionsServiceImpl implements  ActiveSessionsService{

    @Autowired
    ActiveSessionsRepository activeSessionsRepository;

    @Override
    public ActiveSessions saveSession(ActiveSessions activeSession) {
        return activeSessionsRepository.save(activeSession);
    }

    @Override
    public void deleteSession(ActiveSessions activeSessions) {
        activeSessionsRepository.delete(activeSessions);
    }

    @Override
    public void deleteSessionById(String refreshToken) {
        activeSessionsRepository.deleteById(refreshToken);
    }

    @Override
    public ActiveSessions getSessionByToken(String token) {
        return activeSessionsRepository.getOne(token);
    }

    @Override
    public boolean existsById(String refreshToken) {
        return activeSessionsRepository.existsById(refreshToken);
    }
}
