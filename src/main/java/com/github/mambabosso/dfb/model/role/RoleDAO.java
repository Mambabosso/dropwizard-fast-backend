package com.github.mambabosso.dfb.model.role;

import com.github.mambabosso.dfb.dao.BaseDAO;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.mambabosso.dfb.util.ConditionUtils.*;

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
    public List<Role> all(long offset, long limit) {
        return query().select(_role).from(_role).fetch();
    }

    @Override
    public long update(@NonNull UUID id, @NonNull Role role) {
        HibernateUpdateClause clause = update(_role).where(_role.id.eq(id));
        ifNotNull(role.getName(), (x) -> clause.set(_role.name, x));
        ifNotNull(role.isLocked(), (x) -> clause.set(_role.locked, x));
        long result = clause.execute();
        if (result > 0) {
            refresh(getById(id));
        }
        return result;
    }

    @Override
    public long delete(@NonNull UUID id) {
        return delete(_role).where(_role.id.eq(id)).execute();
    }

    public Optional<Role> getRoleByName(@NonNull String name) {
        return Optional.ofNullable(query().select(_role).from(_role).where(_role.name.eq(name)).fetchFirst());
    }

}
