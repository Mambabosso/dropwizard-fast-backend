package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.model.password.PasswordDAO;
import com.github.mambabosso.dfb.model.role.Role;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.joda.time.DateTime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class UserDAOTest {

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Password.class)
            .addEntityClass(Role.class)
            .addEntityClass(User.class)
            .build();

    private static PasswordDAO passwordDAO;
    private static UserDAO dao;

    @BeforeAll
    public static void init() {
        passwordDAO = new PasswordDAO(daoTestRule.getSessionFactory());
        dao = new UserDAO(daoTestRule.getSessionFactory());
    }

    @AfterAll
    public static void close() {
        daoTestRule.after();
    }

    @Test
    @Order(1)
    public void test() {

        DateTime now = DateTime.now();

        Password p = daoTestRule.inTransaction(() -> {
            UUID id = passwordDAO.insert(Password.builder().hash("hash").lastAccess(now).createdAt(now).build());
            return passwordDAO.getById(id);
        });

        UUID id = daoTestRule.inTransaction(() -> {
            return dao.insert(User.builder().name("user").password(p).createdAt(now).build());
        });

        Assertions.assertEquals("user", daoTestRule.inTransaction(() -> {
            return dao.getById(id).getName();
        }));

        Assertions.assertEquals(1, daoTestRule.inTransaction(() -> {
            return dao.update(id, User.builder().name("xxxx").build());
        }));

        Assertions.assertEquals(1, daoTestRule.inTransaction(() -> {
            return dao.delete(id);
        }));

        Assertions.assertNull(daoTestRule.inTransaction(() -> {
            return dao.getById(id);
        }));

    }

}
