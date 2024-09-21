package com.ecommerce.userservice.security.models;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@Table(name = "`client`")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Client {
    @Id
    private String id;
    private String clientId;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    @Column(length = 1000)
    @Lob
    private String clientAuthenticationMethods;
    @Column(length = 1000)
    @Lob
    private String authorizationGrantTypes;
    @Column(length = 1000)
    @Lob
    private String redirectUris;
    @Column(length = 1000)
    @Lob
    private String postLogoutRedirectUris;
    @Column(length = 1000)
    @Lob
    private String scopes;
    @Column(length = 2000)
    @Lob
    private String clientSettings;
    @Column(length = 2000)
    @Lob
    private String tokenSettings;

}
