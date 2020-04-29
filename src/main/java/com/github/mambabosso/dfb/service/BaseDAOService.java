package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.dao.BaseDAO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;

public abstract class BaseDAOService<T extends BaseDAO<? extends Serializable, ? extends Serializable>> {

    @Getter(AccessLevel.PROTECTED)
    private final T baseDAO;

    protected BaseDAOService(@NonNull final T baseDAO) {
        this.baseDAO = baseDAO;
    }

}
