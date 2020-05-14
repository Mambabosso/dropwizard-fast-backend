package com.github.mambabosso.dfb.model.token;

import com.github.mambabosso.dfb.model.Expireable;
import com.github.mambabosso.dfb.model.Persistable;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "token")
public final class Token implements Persistable<UUID>, Expireable {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "token_id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "value", updatable = false)
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", updatable = false)
    private TokenType type;

    @NotNull
    @Column(name = "expires_at", updatable = false)
    private DateTime expiresAt;

    @NotNull
    @Column(name = "last_access")
    private DateTime lastAccess;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private DateTime createdAt;

    @NotNull
    @Column(name = "locked")
    private boolean locked;

}
