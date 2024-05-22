package com.ecommerce.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Token extends BaseModel {
    private String value;
    private Date expiryDate;
    private TokenType type;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
