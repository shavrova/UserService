package com.tms.api.users.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
public class User extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private String encryptedPassword;

    @Column(nullable = false, unique = true)
    private String userId;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String imageUrl;

    //TODO: use List<> if need to set USER, ADMIN roles to admin
    private Integer role;

    private Integer status;

    private Boolean deleted;


    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

//    //TODO: make roles enum
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"))
//    private Collection<Role> roles;

    public User(String firstName, String lastName, String email, String encryptedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    public User(String firstName, String lastName, String email, String encryptedPassword, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }
}