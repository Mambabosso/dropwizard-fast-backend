package com.github.mambabosso.dfb.model.token;

import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class TokenDAOTest {

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Token.class)
            .build();

    private static TokenDAO dao;

    @BeforeAll
    public static void init() {
        dao = new TokenDAO(daoTestRule.getSessionFactory());
    }

    @Test
    @Order(1)
    public void test() {

        DateTime now = DateTime.now();

        UUID id = daoTestRule.inTransaction(() -> {
            return dao.insert(Token.builder().value("token").type(TokenType.JWT).expiresAt(now).lastAccess(now).createdAt(now).build());
        });

        Assertions.assertEquals(1, daoTestRule.inTransaction(() -> {
            return dao.update(id, Token.builder().lastAccess(now.plus(Duration.standardDays(2))).build());
        }));

        Assertions.assertEquals(now.plus(Duration.standardDays(2)), daoTestRule.inTransaction(() -> {
            return dao.getById(id).getLastAccess();
        }));

        Assertions.assertEquals(1, daoTestRule.inTransaction(() -> {
            return dao.delete(id);
        }));

        Assertions.assertNull(daoTestRule.inTransaction(() -> {
            return dao.getById(id);
        }));

    }

}
