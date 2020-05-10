package com.github.mambabosso.dfb.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class EncodeDecodeTest {

    private static final DefaultEncoder encoder = new DefaultEncoder(Algorithm.HMAC256("test"));
    private static final DefaultDecoder decoder = new DefaultDecoder(Algorithm.HMAC256("test"));

    @Test
    public void test() {

        Role r1 = Role.builder().name("Role1").build();
        Role r2 = Role.builder().name("Role2").build();
        Role r3 = Role.builder().name("Role3").build();
        User u1 = User.builder().name("User1").roles(new HashSet<>(Arrays.asList(r1, r2, r3))).build();

        String token = encoder.encode(u1).get();

        assertEquals("User1", decoder.decode(token).get().getName());
        assertEquals(3, decoder.decode(token).get().getRoles().size());

    }

}
