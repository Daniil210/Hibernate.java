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

        try {
            session = Util.getSessionFactory().openSession();

            SQLQuery query = session.createSQLQuery(createTable);
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session == null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        Session session = null;
        SessionFactory sessionFactory =  Util.getSessionFactory();

        try {
            session = sessionFactory.openSession();
            SQLQuery query = session.createSQLQuery("drop table if exists User");
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(session == null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if(session == null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            User user = new User();
            user.setId(id);
            session.delete(user);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if(session == null) {
                session.close();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        Session session = null;

        List<User> list = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Query sqlQuery = session.createQuery("FROM User");
            list = sqlQuery.list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if(session == null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = null;

        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if(session == null) {
                session.close();
            }
        }
    }
}
