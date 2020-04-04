package com.tms.api.users.entity;

import lombok.*;

import javax.persistence.Entity;

@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
public class Role extends BaseEntity {
    private String name;
}