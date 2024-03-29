package net.akul.phone_stock.jdbc;

import com.sun.istack.internal.NotNull;

import net.akul.phone_stock.model.PhoneModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PhoneModelDAOTest {

    @NotNull
    private DAO<PhoneModel, String> dao;

    @NotNull
    private Connection connection;

    @NotNull
    private final PhoneModel model = new PhoneModel();

    @Before
    public void before() {
        model.setName("test");
        try {
            String user = "postgres";
            String password = "root";
            String url = "jdbc:postgresql://localhost:5432/phones_stock";
            connection = DriverManager.getConnection(url, user, password);
            dao = new PhoneModelDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        dao.delete(model);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void whenModelIsExistThenReturnPhoneModelWithId() {
        final PhoneModel result = dao.read("samsung");
        assertThat(result, is(new PhoneModel(1, "samsung")));
    }

    @Test
    public void whenAddPhoneModelWhichAlreadyExistThenReturnFalse() {
        dao.create(model);
        final boolean result = dao.create(model);
        assertThat(result, is(false));
    }

    @Test
    public void whenDeletePhoneModelWhichExistThenReturnTrue() {
        dao.create(model);
        final boolean result = dao.delete(model);
        assertThat(result, is(true));
    }

    @Test
    public void whenDeletePhoneModelWhichNotExistThenReturnFalse() {
        final boolean result = dao.delete(model);
        assertThat(result, is(false));
    }


    @Test
    public void whenUpdateSuccessThenModelNameSetNewValue() {
        dao.create(model);
        model.setName("updated");
        model.setId(dao.read("test").getId());
        final boolean result = dao.update(model);

        assertThat(dao.read("updated").getName(), is("updated"));
    }

    @Test
    public void whenTryUpdatePhoneModelWhichNotExistThenReturnFalse() {
        final boolean result = dao.update(new PhoneModel(Integer.MAX_VALUE, "nnnnn"));
        assertThat(result, is(false));
    }
}