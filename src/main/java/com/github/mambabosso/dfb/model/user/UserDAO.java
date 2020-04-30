package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.dao.BaseDAO;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Optional;
import java.util.UUID;

public class UserDAO extends BaseDAO<User, UUID> {

    private final QUser _user = QUser.user;

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<User> getById(@NonNull UUID id) {
        return Optional.ofNullable(query().select(_user).from(_user).where(_user.id.eq(id)).fetchFirst());
    }

    @Override
    public UUID insert(@NonNull User user) {
        return (UUID)save(user);
    }

    @Override
    public boolean update(@NonNull UUID id, @NonNull User user) {
        HibernateUpdateClause clause = update(_user).where(_user.id.eq(id));
        clause.set(_user.name, user.getName());
        clause.set(_user.password, user.getPassword());
        clause.set(_user.roles, user.getRoles());
        clause.set(_user.locked, user.isLocked());
        return clause.execute() > 0;
    }

    @Override
    public boolean delete(@NonNull UUID id) {
        return delete(_user).where(_user.id.eq(id)).execute() > 0;
    }

    public Optional<User> getUserByName(@NonNull String name) {
        return Optional.ofNullable(query().select(_user).from(_user).where(_user.name.eq(name)).fetchFirst());
    }

}
