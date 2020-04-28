package com.github.mambabosso.dropwizard.fast.backend.model.role;

import com.github.mambabosso.dropwizard.fast.backend.dao.BaseDAO;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Optional;
import java.util.UUID;

public class RoleDAO extends BaseDAO<Role> {

    private final QRole role = QRole.role;

    public RoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public UUID create(Role role) {
        return (UUID)save(role);
    }

    public Optional<Role> getRoleByName(@NonNull String name) {
        return Optional.ofNullable(query().select(role).from(role).where(role.name.eq(name)).fetchFirst());
    }

}
