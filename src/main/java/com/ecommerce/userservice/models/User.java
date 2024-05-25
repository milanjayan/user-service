package com.ecommerce.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
public class User extends BaseModel {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    private boolean isVerified = false;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens = new ArrayList<>();
    private int tokenCount = 0;
}
