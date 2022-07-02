package ru.job4j.hibernate.hql;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.hql.entity.Candidate;
import ru.job4j.hibernate.hql.entity.DbVacancies;
import ru.job4j.hibernate.hql.entity.Vacancy;

import javax.persistence.Query;
import java.util.List;

public class Hql {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Candidate candidate = Candidate.of("test fetch", 5, 100);
            Vacancy vacancy = Vacancy.of("Vacancy", "description");
            DbVacancies dbVacancies = new DbVacancies();
            dbVacancies.add(vacancy);
            candidate.setDbVacancies(dbVacancies);
            session.save(candidate);
            session.getTransaction().commit();
            session.close();
            loadFetchCandidate(candidate.getId(), sf);
            /*
            Session session = sf.openSession();
            session.beginTransaction();
            Candidate candidate1 = Candidate.of("Alex", 5, 500);
            Candidate candidate2 = Candidate.of("Oleg", 1, 100);
            Candidate candidate3 = Candidate.of("Ivan", 3, 300);
            session.save(candidate1);
            session.save(candidate2);
            session.save(candidate3);
            session.getTransaction().commit();
            session.close();

            System.out.println("Метод findAll");
            for (Candidate candidate : findAll(sf)) {
                System.out.println(candidate);
            }
            System.out.println("------------------------------");
            System.out.println("Метод findById");
            System.out.println(findById(2, sf));
            System.out.println("------------------------------");
            System.out.println("Метод findByName");
            for (Candidate candidate : findByName("Oleg", sf)) {
                System.out.println(candidate);
            }
            System.out.println("------------------------------");
            System.out.println("Метод update");
            Candidate candidate = findById(2, sf);
            System.out.println("До изменения");
            System.out.println(candidate);
            candidate.setSalary(1000);
            update(candidate, sf);
            System.out.println("После изменения");
            System.out.println(findById(2, sf));
            System.out.println("------------------------------");
            System.out.println("Метод delete");
            Candidate candidateDelete = findById(2, sf);
            System.out.println("До удаления");
            System.out.println(candidateDelete);
            delete(2, sf);
            System.out.println("После удаления");
            System.out.println(findById(2, sf));
            System.out.println("------------------------------");
             */
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static List<Candidate> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Candidate> rsl = session.createQuery("from Candidate").getResultList();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public static Candidate findById(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Candidate rsl = session.get(Candidate.class, id);
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public static List<Candidate> findByName(String name, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Candidate where name = :fName");
        query.setParameter("fName", name);
        List<Candidate> rsl = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public static void update(Candidate candidate, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "update Candidate set name = :fName, experience = :fExperience, salary = :fSalary where id =:fId");
        query.setParameter("fName", candidate.getName());
        query.setParameter("fExperience", candidate.getExperience());
        query.setParameter("fSalary", candidate.getSalary());
        query.setParameter("fId", candidate.getId());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                "delete from Candidate where id =:fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public static void loadFetchCandidate(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Candidate rsl = session.createQuery(
                "select distinct c from Candidate c "
                        + "join fetch c.dbVacancies db "
                        + "join fetch db.vacancies v "
                        + "where c.id = :fId", Candidate.class
        ).setParameter("fId", id).uniqueResult();
        session.getTransaction().commit();
        session.close();
        System.out.println("ee");
        System.out.println(rsl);
    }
}
