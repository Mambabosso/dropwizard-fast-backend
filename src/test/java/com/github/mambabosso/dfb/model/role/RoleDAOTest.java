package com.github.mambabosso.dfb.model.role;

import com.github.mambabosso.dfb.model.password.Password;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.joda.time.DateTime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class RoleDAOTest {

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Role.class)
            .build();

    private static RoleDAO dao;

    @BeforeAll
    public static void init() {
        dao = new RoleDAO(daoTestRule.getSessionFactory());
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
            return dao.insert(Role.builder().name("role").createdAt(now).build());
        });

        Assertions.assertEquals("role", daoTestRule.inTransaction(() -> {
            return dao.getById(id).getName();
        }));

        Assertions.assertEquals(1, daoTestRule.inTransaction(() -> {
            return dao.update(id, Role.builder().name("xxxx").build());
        }));

        Assertions.assertEquals(1, daoTestRule.inTransaction(() -> {
            return dao.delete(id);
        }));

        Assertions.assertNull(daoTestRule.inTransaction(() -> {
            return dao.getById(id);
        }));

    }

}
