package com.github.mambabosso.dfb.jwt;

import com.github.mambabosso.dfb.util.Result;

import java.security.Principal;

public interface Decoder<T extends Principal> {

    public Result<T> decode(String token);

}
