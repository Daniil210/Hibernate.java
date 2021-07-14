package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS `mysqltest`.`User` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));";

        Session session = null;

        session = Util.getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery(createTable);
        query.executeUpdate();

        if(session != null) {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {

        Session session = null;
        SessionFactory sessionFactory =  Util.getSessionFactory();

        session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery("drop table if exists User");
        query.executeUpdate();

        if(session != null) {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = null;
        session = Util.getSessionFactory().openSession();

        User user = new User(name, lastName, age);
        session.save(user);
        if(session != null) {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {

        Session session = null;
        session = Util.getSessionFactory().openSession();

        User user = new User();
        user.setId(id);
        session.delete(user);

        if(session != null) {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {

        Session session = null;
        List<User> list = null;

        session = Util.getSessionFactory().openSession();
        Query sqlQuery = session.createQuery("FROM User");
        list = sqlQuery.list();


        if(session != null) {
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {

        Session session = null;
        session = Util.getSessionFactory().openSession();

        Query query = session.createQuery("DELETE FROM User");
        query.executeUpdate();

        if(session != null) {
            session.close();
        }
    }
}
