package com.example.token;

import com.example.repo.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component

public class TokenService implements PersistentTokenRepository {

    private TokenRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    public TokenService(TokenRepository tokenRepository){
        repository = tokenRepository;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {

        logger.info("CREATE_TOKEN: username= " + token.getUsername() + " series= " + token.getSeries() + " tokenValue= " + token.getTokenValue());

        repository.save(new TokenCookies(token.getUsername(), token.getSeries(),
                token.getTokenValue(), token.getDate()));
    }

    @Override
    public void updateToken(String series, String value, Date lastUsed) {
        TokenCookies token = repository.findBySeries(series);
        repository.save(new TokenCookies(token.getId(), token.getUsername(), series, value, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        TokenCookies token = repository.findBySeries(seriesId);
        if (token!=null)
            logger.info("GET_TOKEN: series= "+seriesId + " value= " + token.getTokenValue() + " username= " + token.getUsername());
        return token;
    }

    @Override
    public void removeUserTokens(String username) {
        List<TokenCookies> tokens = repository.findByUsername(username);
        if (tokens.size()!=0) {
            for (TokenCookies token : tokens) {
                logger.info("REMOVE_TOKEN: username= " + token.getUsername() + " series= " + token.getSeries() + " tokenValue= " + token.getTokenValue());
                repository.delete(token);
            }
        }
    }
}

