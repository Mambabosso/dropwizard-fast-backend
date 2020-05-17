package com.github.mambabosso.dfb.model.token;

import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class TokenDAOTest {

    private static final DateTime now = DateTime.now();
    private static final DateTime expiresAt = now.plus(Duration.standardMinutes(5));

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Token.class)
            .build();

    private static TokenDAO dao;

    private static UUID id1;
    private static UUID id2;

    @BeforeAll
    public static void init() {
        dao = new TokenDAO(daoTestRule.getSessionFactory());
    }

    @Test
    @Order(1)
    void insert() {

        id1 = daoTestRule.inTransaction(() -> {
            return dao.insert(Token.builder().value("token1").type(TokenType.CUSTOM).expiresAt(expiresAt).lastAccess(now).createdAt(now).build());
        });

        assertNotNull(id1);

        id2 = daoTestRule.inTransaction(() -> {
            return dao.insert(Token.builder().value("token2").type(TokenType.ID).expiresAt(expiresAt).lastAccess(now).createdAt(now).build());
        });

        assertNotNull(id2);

    }

    @Test
    @Order(2)
    void all() {

        assertEquals(2, daoTestRule.inTransaction(() -> dao.all().size()));

    }

    @Test
    @Order(3)
    void get() {

        assertEquals("token1", daoTestRule.inTransaction(() -> dao.getById(id1).getValue()));

        assertEquals("token2", daoTestRule.inTransaction(() -> dao.getById(id2).getValue()));

        assertEquals("token1", daoTestRule.inTransaction(() -> dao.getByValue("token1").getValue()));

        assertEquals("token2", daoTestRule.inTransaction(() -> dao.getByValueAndType("token2", TokenType.ID).getValue()));

        assertNull(daoTestRule.inTransaction(() -> dao.getByValueAndType("token1", TokenType.ID)));

    }

    @Test
    @Order(4)
    void update() {

        assertFalse(daoTestRule.inTransaction(() -> dao.getById(id1).isLocked()));

        long result = daoTestRule.inTransaction(() -> {
            return dao.update(id1, Token.builder().locked(true).build());
        });

        assertEquals(1, result);

        assertTrue(daoTestRule.inTransaction(() -> dao.getById(id1).isLocked()));

    }

    @Test
    @Order(5)
    void delete() {

        long result = daoTestRule.inTransaction(() -> {
            return dao.delete(id2);
        });

        assertEquals(1, result);

        assertNull(daoTestRule.inTransaction(() -> dao.getById(id2)));

        assertEquals(1, daoTestRule.inTransaction(() -> dao.all().size()));

    }

    @Test
    @Order(6)
    void deleteAllExpired() {

        daoTestRule.inTransaction(() -> {
            return dao.insert(Token.builder().value("token3").type(TokenType.CUSTOM).expiresAt(now.minusMinutes(3)).lastAccess(now).createdAt(now).build());
        });

        daoTestRule.inTransaction(() -> {
            return dao.insert(Token.builder().value("token4").type(TokenType.CUSTOM).expiresAt(now.minusMinutes(3)).lastAccess(now).createdAt(now).build());
        });

        assertEquals(3, daoTestRule.inTransaction(() -> dao.all().size()));

        assertEquals(2, daoTestRule.inTransaction(() -> dao.deleteAllExpired()));

        assertEquals(1, daoTestRule.inTransaction(() -> dao.all().size()));

    }

}
