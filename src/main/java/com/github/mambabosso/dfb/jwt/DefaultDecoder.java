package com.github.mambabosso.dfb.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.util.CollectionUtils;
import com.github.mambabosso.dfb.util.Result;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class DefaultDecoder implements Decoder<User> {

    private final Algorithm algorithm;

    private User mapToUser(Map<String, Object> map) {
        User result = new User();
        result.setName(map.get("name").toString());
        result.setRoles(CollectionUtils.streamToSet(CollectionUtils.objectToList(map.get("roles")).stream(), (name) -> {
            return Role.builder().name(name.toString()).build();
        }));
        return result;
    }

    @Override
    public Result<User> decode(String token) {
        try {
            Verification verification = JWT.require(algorithm);
            verification.withIssuer("dfb");
            DecodedJWT jwt = verification.build().verify(token);

            User user = mapToUser(jwt.getClaim("user").asMap());

            return Result.success(user);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get(ex));
        }
    }

}
