package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args)  {

        UserServiceImpl UserServiceImpl = new UserServiceImpl();

        UserServiceImpl.createUsersTable();
        UserServiceImpl.saveUser("Федот", "Алегатров", (byte)79);
        UserServiceImpl.saveUser("Андрей", "Блинов", (byte)21);
        UserServiceImpl.saveUser("Ирина", "Хакамада", (byte)33);
        for (User user : UserServiceImpl.getAllUsers()){
            System.out.println(user);
        }
        UserServiceImpl.cleanUsersTable();
        UserServiceImpl.dropUsersTable();



        /*UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Алексей" , "Курков", (byte) 19);
        userDao.saveUser("Феофан" , "Петров", (byte) 32);
        userDao.saveUser("Евгений" , "Шилов", (byte) 47);
        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();*/




    }
}
