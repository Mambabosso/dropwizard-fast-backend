package com.github.mambabosso.dfb.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.util.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserEncoder implements Encoder<User> {

    private final Algorithm algorithm;

    private Map<String, Object> userToMap(@NonNull final User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", user.getName());
        result.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return result;
    }

    @Override
    public Result<String> encode(final User user) {
        try {
            DateTime now = DateTime.now();
            DateTime expiresAt = now.plus(Duration.standardMinutes(10));

            JWTCreator.Builder builder = JWT.create();
            builder.withIssuer("dfb");
            builder.withIssuedAt(now.toDate());
            builder.withExpiresAt(expiresAt.toDate());
            builder.withClaim("user", userToMap(user));

            String token = builder.sign(algorithm);

            return Result.success(token);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get(ex));
        }
    }

}
