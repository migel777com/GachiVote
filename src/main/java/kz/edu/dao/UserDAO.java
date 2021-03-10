package kz.edu.dao;

import kz.edu.model.Question;
import kz.edu.model.Role;
import kz.edu.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDAO
{
    private final SessionFactory sessionFactory;
    Session session;
    List<User> userList;

    @Autowired
    public UserDAO(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getUserList()
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            Query<User> query = session.createQuery(criteria);
            userList = query.getResultList();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return userList;
    }

    public User findByUserName(String username)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user;
        try
        {
            CriteriaBuilder builder1 = session.getCriteriaBuilder();
            CriteriaQuery<User> q1 = builder1.createQuery(User.class);
            Root<User> root1 = q1.from(User.class);

            Predicate predicateUsername = builder1.equal(root1.get("email"), username);
            user = session.createQuery(q1.where(predicateUsername)).getSingleResult();
            //System.out.println("available authorities:"+user.getRole().getAuthorities());
            //System.out.println("USER DAO. Email:"+user.getEmail()+". Role: "+user.getRole().getId()+". Password: "+user.getPassword());
        }
        catch (NoResultException noResultException)
        {
            user = null;
        }
        finally
        {
            //session.close();
        }
        return user;
    }

    public User findByUserId(int id)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user;
        try
        {
            CriteriaBuilder builder1 = session.getCriteriaBuilder();
            CriteriaQuery<User> q1 = builder1.createQuery(User.class);
            Root<User> root1 = q1.from(User.class);

            Predicate predicateUsername = builder1.equal(root1.get("user_id"), id);
            user = session.createQuery(q1.where(predicateUsername)).getSingleResult();
            //System.out.println("available authorities:"+user.getRole().getAuthorities());
            //System.out.println("USER DAO. Email:"+user.getEmail()+". Role: "+user.getRole().getId()+". Password: "+user.getPassword());
        }
        catch (NoResultException noResultException)
        {
            user = null;
        }
        finally
        {
            //session.close();
        }
        return user;
    }

    public void addUser(User user)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder1 = session.getCriteriaBuilder();
            CriteriaQuery<Role> q1 = builder1.createQuery(Role.class);
            Root<Role> root1 = q1.from(Role.class);

            Predicate predicateRole = builder1.equal(root1.get("name"), "ROLE_USER");
            //Predicate predicateRole = builder1.equal(root1.get("name"), "ROLE_ADMIN");
            Role role = session.createQuery(q1.where(predicateRole)).getSingleResult();
            user.setRole(role);

            session.persist(user);
            session.getTransaction().commit();
        }
        finally
        {
            //session.close();
        }
    }

    public void updateUser(User user, int id)
    {
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user.setId(id);
            session.merge(user);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void deleteUser(int user_id)
    {
        System.out.println("delete " + user_id);
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            User question = session.find(User.class, user_id);
            session.remove(question);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }
}