package com.innova.service;

import com.innova.model.TokenBlacklist;
import com.innova.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistServiceImpl implements  TokenBlacklistService{

    @Autowired
    TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public TokenBlacklist save(TokenBlacklist tokenBlacklist) {
        return tokenBlacklistRepository.save(tokenBlacklist);
    }
}
