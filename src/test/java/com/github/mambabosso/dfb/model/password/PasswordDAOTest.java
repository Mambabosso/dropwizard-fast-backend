package com.github.mambabosso.dfb.model.password;

import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.joda.time.DateTime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class PasswordDAOTest {

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Password.class)
            .build();

    private static PasswordDAO dao;

    @BeforeAll
    public static void init() {
        dao = new PasswordDAO(daoTestRule.getSessionFactory());
    }

    @AfterAll
    public static void close() {
        daoTestRule.after();
    }

    @Test
    @Order(1)
    public void test() {

        DateTime now = DateTime.now();

        UUID id = daoTestRule.inTransaction(() -> {
            return dao.insert(Password.builder().hash("hash").lastAccess(now).createdAt(now).build());
        });

        Assertions.assertEquals("hash", daoTestRule.inTransaction(() -> {
            return dao.getById(id).getHash();
        }));

        Assertions.assertEquals(1, daoTestRule.inTransaction(() -> {
            return dao.update(id, Password.builder().hash("xxxx").lastAccess(now).build());
        }));

        Assertions.assertEquals(1, daoTestRule.inTransaction(() -> {
            return dao.delete(id);
        }));

        Assertions.assertNull(daoTestRule.inTransaction(() -> {
            return dao.getById(id);
        }));

    }

}
