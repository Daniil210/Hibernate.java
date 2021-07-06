package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = Util.getConnection().createStatement()) {

            String createTable = "CREATE TABLE `mysqltest`.`userTab` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));";

            statement.executeUpdate(createTable);
            System.out.println("Таблица создана");
        } catch (SQLException exc) {
            System.out.println("Таблица с таким именем уже была создана.");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {

            statement.executeUpdate("drop table userTab");
            System.out.println("Таблица удалена!");
        } catch (SQLException exc) {
            System.out.println("Таблицы с таким имененем не сущуствует.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        User user = new User(name, lastName, age);
        try (Statement statement = Util.getConnection().createStatement()){

            statement.executeUpdate("insert into userTab (name, lastName, age) values('" + name + "',  '"
                    + lastName + "', " + age + ");");
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate("delete from userTab where id = " + id + ";");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> lu = new ArrayList<>();
        Connection connection = null;
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();

            connection.setAutoCommit(false);
            ResultSet result = statement.executeQuery("select * from userTab ;");
            while (result.next()) {
                User user = new User();
                user.setId((long)result.getInt(1));
                user.setName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getByte(4));
                lu.add(user);
            }
            connection.commit();
        } catch (SQLException exc) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            exc.printStackTrace();
        } finally {
            if (connection != null ) {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        }
        return lu;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){

            statement.executeUpdate("delete from userTab;");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
