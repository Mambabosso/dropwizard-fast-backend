package com.github.mambabosso.dfb.model.password;

import com.github.mambabosso.dfb.dao.BaseDAO;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.UUID;

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
    public Password getById(@NonNull UUID id) {
        return query().select(_password).from(_password).where(_password.id.eq(id)).fetchFirst();
    }

    @Override
    public long update(@NonNull UUID id, @NonNull Password password) {
        HibernateUpdateClause clause = update(_password).where(_password.id.eq(id));
        clause.set(_password.hash, password.getHash());
        clause.set(_password.locked, password.isLocked());
        return clause.execute();
    }

    @Override
    public long delete(@NonNull UUID id) {
        return delete(_password).where(_password.id.eq(id)).execute();
    }

}
