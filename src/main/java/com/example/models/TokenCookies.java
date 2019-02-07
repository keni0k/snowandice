package com.example.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token_cookies")
public class TokenCookies extends PersistentRememberMeToken {

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter private String tokenValue = "";
    @Getter private String username = "";
    @Getter private String series = "";
    @Getter @Setter private Date date;

    @PersistenceConstructor
    public TokenCookies(String username, String series, String tokenValue, Date date) {
        super(username, series, tokenValue, date);
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    @PersistenceConstructor
    public TokenCookies(long id, String username, String series, String tokenValue, Date date) {
        super(username, series, tokenValue, date);
        this.id = id;
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    public TokenCookies(){
        super("","","",new Date());
    }


}