package com.shop.food.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.food.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false, unique = true)
    private String email;
    private String fullName;
    @Column(nullable = false, unique = true, length = 32)
    private String phoneNumber;
    @Column(length = 256)
    @JsonIgnore
    private String password;
    private Integer timeZone;
    @Column(length = 4)
    private String language;
    @Column(length = 32)
    private String deviceId;
    @Column(length = 256)
    private String photoUrl;
    @Column(length = 108)
    @JsonIgnore
    private String verificationCode;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role = Role.USER;

    @ManyToMany(mappedBy = "members",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Group> groups;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(Integer id) {
        this.setId(id);
    }

    @JsonProperty("groupIds")
    public List<Integer> getGroupIds() {
        if (groups == null || groups.isEmpty()) {
            return new ArrayList<>();
        }
        return groups.stream().map(item -> item.getId()).toList();
    }
}
