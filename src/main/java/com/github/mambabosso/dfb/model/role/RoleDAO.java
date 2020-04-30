package com.github.mambabosso.dfb.model.role;

import com.github.mambabosso.dfb.dao.BaseDAO;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Optional;
import java.util.UUID;

public class RoleDAO extends BaseDAO<Role, UUID> {

    private final QRole _role = QRole.role;

    public RoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UUID insert(@NonNull Role role) {
        return (UUID)save(role);
    }

    @Override
    public boolean update(@NonNull UUID id, @NonNull Role role) {
        HibernateUpdateClause clause = update(_role).where(_role.id.eq(id));
        clause.set(_role.name, role.getName());
        clause.set(_role.locked, role.isLocked());
        return clause.execute() > 0;
    }

    @Override
    public boolean delete(@NonNull UUID id) {
        return delete(_role).where(_role.id.eq(id)).execute() > 0;
    }

    public Optional<Role> getRoleByName(@NonNull String name) {
        return Optional.ofNullable(query().select(_role).from(_role).where(_role.name.eq(name)).fetchFirst());
    }

}
