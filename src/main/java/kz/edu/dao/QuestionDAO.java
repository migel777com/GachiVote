package kz.edu.dao;

import kz.edu.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QuestionDAO {
    private SessionFactory sessionFactory;
    Session session;
    List<Book> booksList;
    @Autowired
    public QuestionDAO(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

}
