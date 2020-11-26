package com.innova.service;

import com.innova.model.ActiveSessions;

public interface ActiveSessionsService {
    public ActiveSessions saveSession(ActiveSessions activeSession);
    public void deleteSession(ActiveSessions activeSessions);
    public void deleteSessionById(String refreshToken);
    public ActiveSessions getSessionByToken(String token);
    public boolean existsById(String refreshToken);
}
