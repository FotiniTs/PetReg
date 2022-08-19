package com.pet.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pet.registry.models.enums.Role;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @NotEmpty
    @NotNull
    @Size(min = 5, message = "Username must have at least 5 characters")
    @Column(nullable = false, unique = true)
    String username;

    @NotEmpty
    @NotNull
    @Size(min = 5, message = "First name must have at least 5 characters")
    @Column(name = "first_name", nullable = false)
    String first_name;

    @NotEmpty
    @NotNull
    @Size(min = 5, message = "Last name must have at least 5 characters")
    @Column(name = "last_name", nullable = false)
    String last_name;

    @NotEmpty
    @NotNull
    @Size(min = 3, message = "City must contain at least 3 characters")
    @Column(name = "city",nullable = false)
    String city;

    @NotEmpty
    @NotNull
    @Size(min = 3, message = "State must contain at least 3 characters")
    @Column(name = "state",nullable = false)
    String state;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @NotNull
    @Column(name = "password",nullable = false)
    @Size(min = 6, message = "Password must have at least 5 characters")
    String password;

    @JsonIgnore
    @Transient
    String confirmPassword;

    @Transient
    String full_name;

    @Column(columnDefinition = "boolean default true", nullable = false)
    boolean enabled = true;

    @Column(length = 32, columnDefinition = "varchar(32) default 'ROLE_CITIZEN'")
    @Enumerated(EnumType.STRING)
    Role role;

    public String getFull_name() {
        return first_name+" "+last_name;
    }


}


