package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Алексей" , "Курков", (byte) 19);
        userDao.saveUser("Феофан" , "Петров", (byte) 32);
        userDao.saveUser("Евгений" , "Шилов", (byte) 47);
        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

    }
}
