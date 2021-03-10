package kz.edu.dao;

import kz.edu.model.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;


@Component
public class QuestionDAO {
    private SessionFactory sessionFactory;
    Session session;
    List<Question> questionList;
    @Autowired
    public QuestionDAO(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public List<Question> getQuestionList()
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Question> criteria = builder.createQuery(Question.class);
            Root<Question> root = criteria.from(Question.class);
            criteria.select(root);
            Query<Question> query = session.createQuery(criteria);
            questionList = query.getResultList();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return questionList;
    }


    public Question getQuestion(String name)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Question question;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Question> q1 = builder.createQuery(Question.class);
            Root<Question> root = q1.from(Question.class);

            Predicate predicateQuestion = builder.equal(root.get("question_title"), name);
            question = session.createQuery(q1.where(predicateQuestion)).getSingleResult();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return question;
    }


    public Question getQuestion(int id)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Question question;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Question> q1 = builder.createQuery(Question.class);
            Root<Question> root = q1.from(Question.class);

            Predicate predicateQuestion = builder.equal(root.get("question_id"), id);
            question = session.createQuery(q1.where(predicateQuestion)).getSingleResult();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return question;
    }


    public void addQuestion(Question question)
    {
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(question);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void updateQuestion(Question question, int id)
    {
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            question.setQuestion_id(id);
            session.merge(question);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void deleteQuestion(int question_id)
    {
        System.out.println("delete " + question_id);
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Question question = session.find(Question.class, question_id);
            session.remove(question);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void vote(int id, int option, int user)
    {
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Question question = session.find(Question.class, id);

            if (question.getAnswered().equals("")) {
                question.setAnswered(question.getAnswered()+user);
            }else {
                question.setAnswered(question.getAnswered()+","+user);
            }


            switch (option) {
                case 1: {
                    question.setAnswer_one_count(question.getAnswer_one_count()+1);
                    break;
                }
                case 2: {
                    question.setAnswer_two_count(question.getAnswer_two_count()+1);
                    break;
                }
                case 3: {
                    question.setAnswer_three_count(question.getAnswer_three_count()+1);
                    break;
                }
                case 4: {
                    question.setAnswer_four_count(question.getAnswer_four_count()+1);
                    break;
                }
            }


            session.merge(question);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

}
