package com.example.utils.security.token;

import com.example.models.TokenCookies;
import com.example.repo.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TokenService implements PersistentTokenRepository {

    private TokenRepository repository;

    @Autowired
    public TokenService(TokenRepository tokenRepository){
        repository = tokenRepository;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {

        log.info("CREATE_TOKEN: = series" + token.getSeries() + " username= " + token.getUsername() + " value= " + token.getTokenValue());

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
            log.info("GET_TOKEN: series= " + seriesId + " username= " + token.getUsername() + " value= " + token.getTokenValue());
        return token;
    }

    @Override
    public void removeUserTokens(String username) {
        List<TokenCookies> tokens = repository.findByUsername(username);
        if (tokens.size()!=0) {
            for (TokenCookies token : tokens) {
                log.info("REMOVE_TOKEN: series= " + token.getSeries() + " username= " + token.getUsername() + " value= " + token.getTokenValue());
                repository.delete(token);
            }
        }
    }
}

