package com.sof.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "client")
public class Client extends Person {

    @Column(nullable = false, unique=true)
    private String clientId;

    @Column(nullable = false, length = 25)
    private String password;

    @Column(nullable = false, length = 1)
    private Boolean status;
}
