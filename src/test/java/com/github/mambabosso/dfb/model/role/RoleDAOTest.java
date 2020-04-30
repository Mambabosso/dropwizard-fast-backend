package com.github.mambabosso.dfb.model.role;

import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class RoleDAOTest {

    private static final DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Role.class)
            .build();

    private static RoleDAO dao;

    @BeforeAll
    public static void init() throws Exception {
        dao = new RoleDAO(daoTestRule.getSessionFactory());
    }

    @Test
    public void insertAndGetRole() {

        assertNotEquals(null, daoTestRule.inTransaction(() -> {
            return dao.insert(Role.builder().name("TEST1").build());
        }));

        assertEquals(true, daoTestRule.inTransaction(() -> {
            return dao.getRoleByName("TEST1").isPresent();
        }));

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> {
            daoTestRule.inTransaction(() -> {
                return dao.insert(Role.builder().name("TEST1").build());
            });
        });

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            daoTestRule.inTransaction(() -> {
                return dao.insert(Role.builder().name(null).build());
            });
        });

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            daoTestRule.inTransaction(() -> {
                return dao.insert(Role.builder().name("").build());
            });
        });

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            daoTestRule.inTransaction(() -> {
                return dao.insert(Role.builder().name(" ").build());
            });
        });

    }

    @Test
    public void updateRole() {

        UUID id = daoTestRule.inTransaction(() -> {
            return dao.insert(Role.builder().name("TEST2").build());
        });

        assertEquals(true, daoTestRule.inTransaction(() -> {
            return dao.update(id, Role.builder().name("TEST3").build());
        }));

        assertEquals(id, daoTestRule.inTransaction(() -> {
            return dao.getRoleByName("TEST3").get().getId();
        }));

    }

    @Test
    public void deleteRole() {

        UUID id = daoTestRule.inTransaction(() -> {
            return dao.getRoleByName("TEST3").get().getId();
        });

        assertEquals(true, daoTestRule.inTransaction(() -> {
            return dao.delete(id);
        }));

        assertEquals(false, daoTestRule.inTransaction(() -> {
            return dao.getRoleByName("TEST3").isPresent();
        }));

    }

}
