package com.github.mambabosso.dfb.model;

import org.joda.time.DateTime;

import java.io.Serializable;

public interface Persistable<PK extends Serializable> extends Serializable {

    public PK getId();

    public DateTime getCreatedAt();

    public boolean isLocked();

}
