package com.github.mambabosso.dfb.model.role;

import com.github.mambabosso.dfb.dao.BaseDAO;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Optional;
import java.util.UUID;

public class RoleDAO extends BaseDAO<Role, UUID> {

    private final QRole role = QRole.role;

    public RoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UUID create(Role role) {
        return (UUID)save(role);
    }

    public Optional<Role> getRoleByName(@NonNull String name) {
        return Optional.ofNullable(query().select(role).from(role).where(role.name.eq(name)).fetchFirst());
    }

}
