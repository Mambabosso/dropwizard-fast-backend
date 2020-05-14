package com.github.mambabosso.dfb.model.password;

import com.github.mambabosso.dfb.dao.BaseDAO;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.UUID;

import static com.github.mambabosso.dfb.util.ConditionUtils.*;

public class PasswordDAO extends BaseDAO<Password, UUID> {

    private final QPassword _password = QPassword.password;

    public PasswordDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UUID insert(@NonNull Password password) {
        return (UUID)save(password);
    }

    @Override
    public List<Password> all(long offset, long limit) {
        return query(offset, limit).select(_password).from(_password).fetch();
    }

    @Override
    public long update(@NonNull UUID id, @NonNull Password password) {
        HibernateUpdateClause clause = update(_password).where(_password.id.eq(id));
        ifNotNull(password.getLastAccess(), (x) -> clause.set(_password.lastAccess, x));
        ifNotNull(password.isLocked(), (x) -> clause.set(_password.locked, x));
        long result = clause.execute();
        if (result > 0) {
            refresh(getById(id));
        }
        return result;
    }

    @Override
    public long delete(@NonNull UUID id) {
        return delete(_password).where(_password.id.eq(id)).execute();
    }

}
