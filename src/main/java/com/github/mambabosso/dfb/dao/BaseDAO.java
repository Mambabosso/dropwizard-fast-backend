package com.github.mambabosso.dfb.dao;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public abstract class BaseDAO<T extends Serializable, PK extends Serializable> extends AbstractDAO<T> {

    protected BaseDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Session session() {
        return currentSession();
    }

    protected Serializable save(@NonNull final T value) {
        return session().save(value);
    }

    protected HibernateUpdateClause update(@NonNull final EntityPath<T> entityPath) {
        return new HibernateUpdateClause(session(), entityPath);
    }

    protected HibernateDeleteClause delete(@NonNull final EntityPath<T> entityPath) {
        return new HibernateDeleteClause(session(), entityPath);
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

    public void refresh(@NonNull final T value) {
        session().refresh(value);
    }

    public void refresh() {
        for (final T t : all()) {
            refresh(t);
        }
    }

    public abstract PK insert(@NonNull final T value);

    public abstract List<T> all(final long offset, final long limit);

    public List<T> all() {
        return all(0, 0);
    }

    public abstract T getById(@NonNull final PK id);

    public abstract long update(@NonNull final PK id, @NonNull final T value);

    public abstract long delete(@NonNull final PK id);

}
