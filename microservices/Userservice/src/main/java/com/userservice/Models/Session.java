package com.userservice.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;

@Getter
@Setter
@Entity
@JsonDeserialize(as = Session.class)
public class Session extends BaseModel{
    private String token;
    private Date expiryAt;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private Sessionstatus sessionStatus;
}
