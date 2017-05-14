package utils.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.sql.Date;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("utils/hibernate/hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

    public static boolean SaveScore(String playerName, String levelName, int steps, long elapsedTime) {
        // get the session object


        // build the score id
        ScoreId scoreId = new ScoreId(playerName, levelName);
        // check if it already exists in the DB

        if (isValidInsert(scoreId, steps, elapsedTime)) {
            Session session = getSessionFactory().openSession();
            Score score = new Score();

            score.setId(scoreId);
            score.setSteps(steps);
            score.setTime(elapsedTime);

            if (!session.getTransaction().isActive())
                session.beginTransaction();


            session.saveOrUpdate(score);
            session.getTransaction().commit();

            session.close();

            return true;
        } else {
            return false;
        }
    }

    private static boolean isValidInsert(ScoreId scoreId, int steps, long elapsedTime) {
        Session session = getSessionFactory().openSession();

        String queryString = "from Score s where s.id= :id";

        if (!session.getTransaction().isActive())
            session.beginTransaction();

        Query query = session.createQuery(queryString);
        query.setParameter("id", scoreId);

        if (query.list().isEmpty())
        {
            session.close();
            return true;
        }
            // check if there is an improvement in steps or time
        else {
            Score score = (Score) query.list().get(0);
            if (steps < score.getSteps() || elapsedTime<score.getTime()) {
                session.close();
                return true;
            }
            else {
                session.close();
                return false;
            }
        }
    }

}
