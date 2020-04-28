package com.github.mambabosso.dropwizard.fast.backend.model.role;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
public final class Role implements Serializable {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "role_id")
    private UUID id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "invalid role name")
    @Column(name = "name", unique = true)
    private String name;

}
