package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.model.role.Role;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class UserDAOTest {

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(User.class)
            .addEntityClass(Role.class)
            .build();

    private static UserDAO dao;

    @BeforeAll
    public static void init() {
        dao = new UserDAO(daoTestRule.getSessionFactory());
    }

    @AfterAll
    public static void close() {
        daoTestRule.after();
    }

    @Test
    @Order(1)
    public void test() {
    }

}
