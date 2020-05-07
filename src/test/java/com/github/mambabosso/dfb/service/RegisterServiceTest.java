package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.model.password.PasswordDAO;
import com.github.mambabosso.dfb.model.password.PasswordService;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.model.user.UserDAO;
import com.github.mambabosso.dfb.model.user.UserService;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.RegexValidator;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

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
        UserService userService = new UserService(new UserDAO(daoTestRule.getSessionFactory()));
        PasswordService passwordService = new PasswordService(new PasswordDAO(daoTestRule.getSessionFactory()));
        service = new RegisterService(userService, passwordService);
    }

    @Test
    @Order(1)
    public void test() {

        String regex = "^[a-zA-Z0-9]{4,50}$";

        Result<User> user = daoTestRule.inTransaction(() ->
            service.register(new RegexValidator("User1", regex), new RegexValidator("12345", regex))
        );

        assertEquals(Errors.USER_ALREADY_EXISTS.get(), daoTestRule.inTransaction(() -> {
            return service.register(new RegexValidator("User1", regex), new RegexValidator("12345", regex)).getError();
        }));

        assertThrows(ConstraintViolationException.class, () -> {
            daoTestRule.inTransaction(() -> {
                service.register(() -> "", new RegexValidator("12345", regex));
            });
        });

        assertEquals(Errors.PASSWORD_VALIDATION_FAILURE.get(), daoTestRule.inTransaction(() -> {
            return service.register(() -> "x", new RegexValidator("x", regex)).getError();
        }));

    }

}
