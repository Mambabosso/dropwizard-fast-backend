package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.model.Persistable;
import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.model.role.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public final class User implements Principal, Persistable<UUID> {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "user_id", updatable = false)
    private UUID id;

    @NotNull
    @Pattern(regexp = "^.{1,50}$", message = "invalid user name")
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password", referencedColumnName = "password_id")
    private Password password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "role"))
    private Set<Role> roles;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private DateTime createdAt;

    @NotNull
    @Column(name = "locked")
    private boolean locked;

    @Override
    public String getName() {
        return name;
    }

}
