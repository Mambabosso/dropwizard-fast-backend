package com.github.mambabosso.dfb.model.role;

import com.github.mambabosso.dfb.model.Persistable;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "role")
public final class Role implements Persistable<UUID> {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "role_id")
    private UUID id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_-]{4,50}$", message = "invalid role name")
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "created_at")
    private DateTime createdAt;

    @NotNull
    @Column(name = "locked")
    private boolean locked;

}
