package com.nuka.nuka_pos.api.user;

import com.nuka.nuka_pos.api.organization.Organization;
import com.nuka.nuka_pos.api.branch.Branch;
import com.nuka.nuka_pos.api.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entity representing a User in the system.
 */
@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String forenames;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    private String profilePicture;

    @Column(nullable = false)
    private Boolean isActive = true;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Enumerated(EnumType.STRING)  // Updated to Enum
    @Column(nullable = false)
    private Role role;  // Updated to Role enum

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name());  // Use enum name as authority
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE.equals(isActive);
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
        return Boolean.TRUE.equals(isActive);
    }
}
