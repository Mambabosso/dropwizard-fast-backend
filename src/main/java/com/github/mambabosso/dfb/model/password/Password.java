package com.github.mambabosso.dfb.model.password;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "password")
public final class Password implements Serializable {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "password_id")
    private UUID id;

    @NotNull
    @Column(name = "hash")
    private String hash;

    @NotNull
    @Column(name = "locked")
    private boolean locked;

}
