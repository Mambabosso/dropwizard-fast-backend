package com.github.mambabosso.dfb.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.util.CollectionUtils;
import com.github.mambabosso.dfb.util.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class UserDecoder implements Decoder<User> {

    private final Algorithm algorithm;

    private User mapToUser(@NonNull final Map<String, Object> map) {
        User result = new User();
        result.setName(map.get("name").toString());
        result.setRoles(CollectionUtils.streamToSet(CollectionUtils.objectToList(map.get("roles")).stream(), (n) -> {
            return Role.builder().name(n.toString()).build();
        }));
        return result;
    }

    @Override
    public Result<User> decode(final String accessToken) {
        try {
            Verification verification = JWT.require(algorithm);
            verification.withIssuer("dfb");
            DecodedJWT jwt = verification.build().verify(accessToken);

            User user = mapToUser(jwt.getClaim("user").asMap());

            return Result.success(user);
        } catch (JWTVerificationException ex) {
            return Result.failure(Errors.INVALID_TOKEN.get(ex));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get(ex));
        }
    }

}
