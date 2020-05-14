package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.model.role.Role;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.joda.time.DateTime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class UserDAOTest {

    private static final DateTime now = DateTime.now();

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Password.class)
            .addEntityClass(Role.class)
            .addEntityClass(User.class)
            .build();

    private static UserDAO dao;

    private static UUID id1;
    private static UUID id2;

    @BeforeAll
    public static void init() {
        dao = new UserDAO(daoTestRule.getSessionFactory());
    }

    @Test
    @Order(1)
    void insert() {

        Password p = Password.builder().hash("hash1").lastAccess(now).createdAt(now).build();

        id1 = daoTestRule.inTransaction(() -> {
            return dao.insert(User.builder().name("user1").password(p).createdAt(now).build());
        });

        assertNotNull(id1);

        id2 = daoTestRule.inTransaction(() -> {
            return dao.insert(User.builder().name("user2").password(p).createdAt(now).build());
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
    void getById() {

        assertEquals("user1", daoTestRule.inTransaction(() -> dao.getById(id1).getName()));

        assertEquals("user2", daoTestRule.inTransaction(() -> dao.getById(id2).getName()));

    }

    @Test
    @Order(4)
    void update() {

        assertFalse(daoTestRule.inTransaction(() -> dao.getById(id1).isLocked()));

        long result = daoTestRule.inTransaction(() -> {
            return dao.update(id1, User.builder().locked(true).build());
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

}
