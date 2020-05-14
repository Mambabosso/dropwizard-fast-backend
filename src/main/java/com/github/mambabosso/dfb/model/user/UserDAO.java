package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.dao.BaseDAO;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.mambabosso.dfb.util.ConditionUtils.*;

public class UserDAO extends BaseDAO<User, UUID> {

    private final QUser _user = QUser.user;

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UUID insert(@NonNull User user) {
        return (UUID)save(user);
    }

    @Override
    public List<User> all(long offset, long limit) {
        return query(offset, limit).select(_user).from(_user).fetch();
    }

    @Override
    public long update(@NonNull UUID id, @NonNull User user) {
        HibernateUpdateClause clause = update(_user).where(_user.id.eq(id));
        ifNotNull(user.getName(), (x) -> clause.set(_user.name, x));
        ifNotNull(user.isLocked(), (x) -> clause.set(_user.locked, x));
        long result = clause.execute();
        if (result > 0) {
            refresh(getById(id));
        }
        return result;
    }

    @Override
    public long delete(@NonNull UUID id) {
        return delete(_user).where(_user.id.eq(id)).execute();
    }

    public Optional<User> getUserByName(@NonNull String name) {
        return Optional.ofNullable(query().select(_user).from(_user).where(_user.name.eq(name)).fetchFirst());
    }

}
