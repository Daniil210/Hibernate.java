package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;
    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {

            String createTable = "CREATE TABLE IF NOT EXISTS `mysqltest`.`userTab` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));";

            statement.executeUpdate(createTable);
            System.out.println("Таблица создана");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("drop table if exists userTab");
            System.out.println("Таблица удалена!");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "insert into userTab (name, lastName, age) values(?, ?, ?);";
        User user = new User(name, lastName, age);

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement statement = connection.prepareStatement("delete from userTab where id = ?;")){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> lu = new ArrayList<>();

        try (Statement statement = connection.createStatement();){

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
        }
        return lu;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){

            statement.executeUpdate("delete from userTab;");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
