package ru.job4j.hibernate.lazy;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.lazy.entity.LazyCarBrand;
import ru.job4j.hibernate.lazy.entity.LazyModel;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            addEntity(sf);
            printEntity(sf);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void addEntity(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        LazyCarBrand lazyCarBrand = LazyCarBrand.of("car brand");
        LazyModel model1 = LazyModel.of("model1", lazyCarBrand);
        LazyModel model2 = LazyModel.of("model2", lazyCarBrand);
        lazyCarBrand.addModel(model1);
        lazyCarBrand.addModel(model2);
        try {
            session.save(lazyCarBrand);
            session.save(model1);
            session.save(model2);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private static void printEntity(SessionFactory sf) {
        Session session = sf.openSession();
        List<LazyCarBrand> carBrands = new ArrayList<>();
        session.beginTransaction();
        try {
            carBrands = session.createQuery(
                    "select distinct b from LazyCarBrand b join fetch b.models"
            ).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        for (LazyCarBrand carBrand : carBrands) {
            System.out.println(carBrand);
        }
    }
}
