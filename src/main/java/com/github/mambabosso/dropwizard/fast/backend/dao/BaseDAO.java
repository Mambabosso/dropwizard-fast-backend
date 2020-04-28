package com.github.mambabosso.dropwizard.fast.backend.dao;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Optional;

public abstract class BaseDAO<T extends Serializable> extends AbstractDAO<T> {

    @Getter
    private final SessionFactory sessionFactory;

    protected BaseDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    protected Session session() {
        return currentSession();
    }

    protected HibernateQuery<T> query(final long offset, final long limit) {
        HibernateQuery<T> query = new HibernateQuery<>(session());
        if (offset > 0) {
            query = query.offset(offset);
        }
        if (limit > 0) {
            query = query.limit(limit);
        }
        return query;
    }

    protected HibernateQuery<T> query() {
        return query(0, 0);
    }

    protected HibernateUpdateClause update(@NonNull final EntityPath<T> entityPath) {
        return new HibernateUpdateClause(session(), entityPath);
    }

    protected Serializable save(@NonNull final T value) {
        return session().save(value);
    }

    protected Optional<T> tryGet(@NonNull final Serializable id) {
        return Optional.ofNullable(session().get(getEntityClass(), id));
    }

}
