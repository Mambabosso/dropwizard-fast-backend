package com.github.mambabosso.dfb.jwt;

import com.github.mambabosso.dfb.util.Result;

import java.security.Principal;

public interface Encoder<T extends Principal> {

    public Result<String> encode(final T principal, final String tokenId);

}
