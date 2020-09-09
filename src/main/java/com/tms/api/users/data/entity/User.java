package com.tms.api.users.data.entity;

import com.tms.api.users.config.security.model.AuthProvider;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/*
//todo remove @Column (nullable and name)
 */


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String encryptedPassword;

    @Column(nullable = false, unique = true)
    private String userId;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    //TODO: implement
    private String imageUrl;

    //TODO: use List<> if need to set USER, ADMIN roles to admin
    private Integer role;

    //TODO: implement disabling/enabling user e.g. /users/{id}?status=disabled
    private Integer status;

    //TODO refactor deleting user - controller/service/repository
    private Boolean deleted;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

//    @Column(nullable = false)
//    private Boolean emailVerified = true;


    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }


    public User(String firstName, String lastName, String email, String encryptedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

}