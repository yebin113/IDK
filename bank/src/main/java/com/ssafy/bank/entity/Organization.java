package com.ssafy.bank.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name="organization")
public class Organization {

    @Id
    @Column(name = "org_code", unique = true)
    private String orgCode;

    @Column(name = "org_name")
    private String orgName;

    @Enumerated(EnumType.STRING)
    @Column(name = "org_type", columnDefinition = "TEXT")
    private OrganizationType orgType;

    @Column(name = "access_token", length = 1000)
    private String accessToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
