package com.shop.food.model.user;

import com.shop.food.model.BaseEntity;
import com.shop.food.model.FamilyGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false, unique = true)
    private String email;
    private String fullName;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @ManyToMany(mappedBy = "familyMembers")
    private List<FamilyGroup> familyGroups;

    @Override
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
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
