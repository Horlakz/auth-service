package com.zeyza.auth.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "auth_users")
public class User extends Base implements Serializable {

    @Size(min = 1, max = 64)
    @Column(name = "username", nullable = false, length = 64, unique = true)
    private String username;

    @Size(min = 1, max = 64)
    @Column(name = "email", nullable = false, length = 64, unique = true)
    private String email;

    @Size(min = 1, max = 64)
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
