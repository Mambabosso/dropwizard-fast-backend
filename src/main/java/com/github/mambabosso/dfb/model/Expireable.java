package com.github.mambabosso.dfb.model;

import org.joda.time.DateTime;

public interface Expireable {

    public DateTime getExpiresAt();

    public default boolean isExpired() {
        return getExpiresAt().isBeforeNow();
    }

}
