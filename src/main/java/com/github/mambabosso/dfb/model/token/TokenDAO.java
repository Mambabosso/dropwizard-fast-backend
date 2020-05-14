package com.github.mambabosso.dfb.model.token;

import com.github.mambabosso.dfb.dao.BaseDAO;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.UUID;

import static com.github.mambabosso.dfb.util.ConditionUtils.*;

public class TokenDAO extends BaseDAO<Token, UUID> {

    private final QToken _token = QToken.token;

    public TokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UUID insert(@NonNull Token token) {
        return (UUID)save(token);
    }

    @Override
    public List<Token> all(long offset, long limit) {
        return query(offset, limit).select(_token).from(_token).fetch();
    }

    public Token getByValue(@NonNull String value) {
        return query().select(_token).from(_token).where(_token.value.eq(value)).fetchFirst();
    }

    @Override
    public long update(@NonNull UUID id, @NonNull Token token) {
        HibernateUpdateClause clause = update(_token).where(_token.id.eq(id));
        ifNotNull(token.getLastAccess(), (x) -> clause.set(_token.lastAccess, x));
        ifNotNull(token.isLocked(), (x) -> clause.set(_token.locked, x));
        long result = clause.execute();
        if (result > 0) {
            refresh(getById(id));
        }
        return result;
    }

    @Override
    public long delete(@NonNull UUID id) {
        long result = delete(_token).where(_token.id.eq(id)).execute();
        if (result > 0) {
            evict(getById(id));
        }
        return result;
    }

}
