package uz.pdp.appmeganews.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appmeganews.entity.template.AbsIntAuditingEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "users")
@SQLRestriction("deleted = false")
@SQLDelete(sql = ("update users set deleted = true where id = ?"))
public class User extends AbsIntAuditingEntity implements UserDetails, Serializable {
    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private boolean enable;

    @ManyToOne
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role.getPermissions() == null || role.getPermissions().isEmpty())
            return new ArrayList<>();

        return role.getPermissions().stream()
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                .toList();
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


}
