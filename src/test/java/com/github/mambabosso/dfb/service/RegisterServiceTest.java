package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.model.password.PasswordDAO;
import com.github.mambabosso.dfb.model.password.PasswordService;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.model.role.RoleDAO;
import com.github.mambabosso.dfb.model.role.RoleService;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.model.user.UserDAO;
import com.github.mambabosso.dfb.model.user.UserService;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class RegisterServiceTest {

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Password.class)
            .addEntityClass(Role.class)
            .addEntityClass(User.class)
            .build();

    private static RegisterService service;

    @BeforeAll
    public static void init() {
        PasswordService passwordService = new PasswordService(new PasswordDAO(daoTestRule.getSessionFactory()));
        RoleService roleService = new RoleService(new RoleDAO(daoTestRule.getSessionFactory()));
        UserService userService = new UserService(new UserDAO(daoTestRule.getSessionFactory()));
        service = new RegisterService(passwordService, roleService, userService);
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
